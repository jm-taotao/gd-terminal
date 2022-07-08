package com.terminal.manage.services.impl;

import com.terminal.manage.base.enums.IsDeleted;
import com.terminal.manage.mapper.MenuMapper;
import com.terminal.manage.model.Menu;
import com.terminal.manage.services.MenuService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

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
        Boolean flag = menuMapper.insertSelective(menu) > 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<Boolean> updateMenu(Menu menu) {
        if (Objects.isNull(menu.getId())){
            return Optional.of(false);
        }
        Boolean flag = menuMapper.updateByPrimaryKeySelective(menu) > 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<Boolean> delMenuById(Long menuId) {
        Menu menu = new Menu();
        menu.setId(menuId);
        menu.setIsDeleted("YES");
        Boolean flag = menuMapper.updateByPrimaryKeySelective(menu) > 0;
        return Optional.of(flag);
    }


    public Optional<List<Menu>> getMenuTree(){
        List<Menu> menuList = menuMapper.selectAll();
        Map<Long, List<Menu>> menuMap = menuList.stream().collect(groupingBy(Menu::getPid));
        List<Menu> parentMenus = menuMap.get(0L);
        getChildren(parentMenus,menuMap);
        return Optional.of(parentMenus);
    }

    private void getChildren(List<Menu> parentMenus,Map<Long, List<Menu>> menuMap){
        parentMenus.forEach(v->{
            if (menuMap.containsKey(v.getId())){
                List<Menu> children = menuMap.get(v.getId());
                children.sort(Comparator.comparing(Menu::getSno));
                v.setChildren(children);
                getChildren(children,menuMap);
            }else {
                v.setChildren(null);
            }
        });
    }
}
