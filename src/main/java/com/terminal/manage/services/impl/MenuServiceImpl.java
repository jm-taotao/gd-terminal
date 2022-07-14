package com.terminal.manage.services.impl;

import com.terminal.manage.base.enums.Constants;
import com.terminal.manage.base.enums.IsDeleted;
import com.terminal.manage.base.excption.BizException;
import com.terminal.manage.mapper.MenuMapper;
import com.terminal.manage.model.Menu;
import com.terminal.manage.services.MenuService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author Jyt
 * @date 2021/9/29
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Optional<List<Menu>> pageMenu(Integer pageStart, Integer pageSize, Menu menu) {
        Example example = Example.builder(Menu.class).build();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",menu.getId())
                .andLike("url","%"+menu.getUrl()+"%")
                .andEqualTo("isDeleted", IsDeleted.NO.code)
                .andLike("name","%"+menu.getName()+"%");

        List<Menu> menuList = menuMapper.selectByExampleAndRowBounds(example, new RowBounds(pageStart, pageSize));
        if (CollectionUtils.isEmpty(menuList)){
            return Optional.of(new ArrayList<>());
        }
        return Optional.of(menuList);
    }

    @Override
    public Optional<Menu> getMenuById(Long menuId) {
        Menu menu = menuMapper.selectByPrimaryKey(menuId);
        return Optional.of(menu);
    }

    @Override
    public Optional<List<Menu>> getMenuListByPid(Long pid) {
        Example example = Example.builder(Menu.class).build();
        example.and().andEqualTo("pid",pid);
        List<Menu> menuList = menuMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(menuList)){
            return Optional.of(new ArrayList<>());
        }
        return Optional.of(menuList);
    }

    @Override
    public Optional<Boolean> addMenu(Menu menu) {
        menu.setIsDeleted(IsDeleted.NO.code);
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        Boolean flag = menuMapper.insertSelective(menu) > 0;
        return Optional.of(flag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Boolean> updateMenu(Menu menu) {
        if (Objects.isNull(menu.getId())){
            return Optional.of(false);
        }
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(menu.getName())){
            criteria.andEqualTo("name",menu.getName());
            Menu menuOld = menuMapper.selectOneByExample(example);
            if (Objects.nonNull(menuOld) && !menu.getId().equals(menuOld.getId())){
                throw new BizException(Constants.MENU_EXIST);
            }
        }
        Menu menuOld = menuMapper.selectByPrimaryKey(menu.getId());
        //后移
        if (menu.getSno() > menuOld.getSno()){
            menuMapper.updateMenuSnoUp(menu.getId(),menu.getSno(),menuOld.getSno());
        }
        //前移
        if (menu.getSno() < menuOld.getSno()){
            menuMapper.updateMenuSnoDown(menu.getId(),menu.getSno(),menuOld.getSno());
        }
        Boolean flag = menuMapper.updateByPrimaryKeySelective(menu) > 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<Boolean> delMenuById(Long menuId) {
        Menu menu = new Menu();
        menu.setId(menuId);
        menu.setIsDeleted("YES");
        menu.setUpdateTime(LocalDateTime.now());

        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",menu.getId())
                .andEqualTo("isDeleted",IsDeleted.NO.code);
        Boolean flag = menuMapper.updateByExampleSelective(menu,example) > 0;
        return Optional.of(flag);
    }


    public Optional<List<Menu>> getMenuTree(Menu menu){
        Example example = new Example(Menu.class,false,false);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(menu.getName())){
            criteria.andLike("name","%" + menu.getName() + "%");
        }
        example.orderBy("sno");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Menu> menuList = menuMapper.selectByExample(example).stream().peek(v->{
            if (Objects.nonNull(v.getCreateTime())){
                v.setCreateTimeStr(dtf.format(v.getCreateTime()));
            }
            if (Objects.nonNull(v.getUpdateTime())){
                v.setCreateTimeStr(dtf.format(v.getUpdateTime()));
            }
        }).collect(Collectors.toList());
        Map<Long, List<Menu>> menuMap = menuList.stream().collect(groupingBy(Menu::getPid));
        if (CollectionUtils.isEmpty(menuMap)){
            return Optional.empty();
        }
        Map<Long, List<Menu>> menuNameMap = menuList.stream().collect(groupingBy(Menu::getId));
        OptionalLong min = menuMap.keySet().stream().mapToLong(Long::longValue).min();
        List<Menu> parentMenus = menuMap.get(min.getAsLong());
        getChildren(parentMenus,menuMap,menuNameMap);
        return Optional.of(parentMenus);
    }

    private void getChildren(List<Menu> parentMenus,Map<Long, List<Menu>> menuMap,Map<Long, List<Menu>> menuNameMap){
        parentMenus.forEach(v->{
            if (menuNameMap.containsKey(v.getPid())){
                v.setPname(menuNameMap.get(v.getPid()).get(0).getName());
            }
            if (menuMap.containsKey(v.getId())){
                List<Menu> children = menuMap.get(v.getId());
                children.sort(Comparator.comparing(Menu::getSno));
                v.setChildren(children);
                getChildren(children,menuMap,menuNameMap);
            }else {
                v.setChildren(null);
            }
        });
    }
}