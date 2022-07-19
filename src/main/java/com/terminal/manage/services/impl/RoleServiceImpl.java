package com.terminal.manage.services.impl;

import com.github.pagehelper.PageInfo;
import com.terminal.manage.base.enums.Constants;
import com.terminal.manage.base.enums.IsDeleted;
import com.terminal.manage.base.excption.BizException;
import com.terminal.manage.mapper.RoleMapper;
import com.terminal.manage.mapper.RoleMenuMapper;
import com.terminal.manage.mapper.UserRoleMapper;
import com.terminal.manage.model.Role;
import com.terminal.manage.model.RoleMenu;
import com.terminal.manage.model.User;
import com.terminal.manage.model.UserRole;
import com.terminal.manage.services.RoleService;
import com.terminal.manage.tool.DataUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author TAO
 * @date 2022/7/16 / 2:12
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Optional<Boolean> addRole(Role role) {
        if (!StringUtils.isEmpty(role.getName())){
            Example example = new Example(Role.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("name",role.getName());
            int count = roleMapper.selectCountByExample(example);
            if (count > 0){
                throw new BizException(Constants.ROLE_EXIST);
            }
        }
        role.setIsDeleted(IsDeleted.NO.code);
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        int row = roleMapper.insertUseGeneratedKeys(role);
        return Optional.of(row>0);
    }

    @Override
    public Optional<Boolean> updateRole(Role role) {
        if (!StringUtils.isEmpty(role.getName())){
            Example example = new Example(Role.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("name",role.getName());
            Role roleOld = roleMapper.selectOneByExample(example);
            if (Objects.nonNull(roleOld) && !roleOld.getId().equals(role.getId())){
                throw new BizException(Constants.ROLE_EXIST);
            }
        }
        role.setUpdateTime(LocalDateTime.now());
        Boolean flag = roleMapper.updateByPrimaryKeySelective(role) < 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<Boolean> delRoleById(Long roleId) {
        if (Objects.isNull(roleId)){
            return Optional.of(false);
        }
        Role role = new Role();
        role.setId(roleId);
        role.setIsDeleted(IsDeleted.YES.code);
        role.setUpdateTime(LocalDateTime.now());
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",role.getId())
                .andEqualTo("isDeleted",IsDeleted.NO.code);
        Boolean flag = roleMapper.updateByExampleSelective(role,example) > 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<PageInfo<Role>> pageRoleList(Integer page, Integer pageSize, Role role) {
        Example example = Example.builder(Role.class).build();
        Example.Criteria criteria = example.createCriteria();
        if (!Objects.isNull(role.getId())){
            criteria.andEqualTo("id",role.getId());
        }
        if (!StringUtils.isEmpty(role.getName())){
            criteria.andLike("name","%"+role.getName()+"%");
        }
        if (!StringUtils.isEmpty(role.getIsDeleted())){
            criteria.andEqualTo("isDeleted", role.getIsDeleted());
        }
        if (!StringUtils.isEmpty(role.getStart())){
            criteria.andGreaterThanOrEqualTo("createTime",role.getStart());
        }
        if (!StringUtils.isEmpty(role.getEnd())){
            criteria.andLessThanOrEqualTo("createTime",role.getEnd());
        }
        List<Role> roles = roleMapper.selectByExampleAndRowBounds(example,new RowBounds(page*pageSize,pageSize));
        DataUtils.formatterDateForList(roles);
        int count = roleMapper.selectCountByExample(example);
        PageInfo<Role> rolePageInfo = new PageInfo<>(roles);
        rolePageInfo.setTotal(count);
        return Optional.of(rolePageInfo);
    }

    @Override
    public Optional<Role> getRoleById(Long roleId) {
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> addUserRole(Long userId, List<Long> roleIds) {
        if (Objects.isNull(userId)){
            return Optional.of(false);
        }
        List<UserRole> userRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        }
        int row = userRoleMapper.insertList(userRoles);
        return Optional.of(row>0);
    }

    @Override
    public Optional<Boolean> removeUserRole(Long userId, List<Long> roleIds) {
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.isNull(userId)){
            return Optional.of(false);
        }
        criteria.andEqualTo("userId",userId);
        if (!CollectionUtils.isEmpty(roleIds)){
            criteria.andIn("roleId",roleIds);
        }
        int row = userRoleMapper.deleteByExample(example);
        return Optional.of(row>0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Boolean> addRoleMenu(Long roleId, List<Long> menuIds) {
        if (Objects.isNull(roleId)){
            return Optional.of(false);
        }

        Example example = new Example(RoleMenu.class);
        example.and().andEqualTo("roleId",roleId);
        roleMenuMapper.deleteByExample(example);

        List<RoleMenu> roleMenus = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);
            roleMenus.add(roleMenu);
        }
        int row = roleMenuMapper.insertList(roleMenus);
        return Optional.of(row>0);
    }

    @Override
    public Optional<Boolean> removeRoleMenu(Long roleId, List<Long> menuIds) {
        Example example = new Example(RoleMenu.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.isNull(roleId)){
            return Optional.of(false);
        }
        criteria.andEqualTo("roleId",roleId);
        if (!CollectionUtils.isEmpty(menuIds)){
            criteria.andIn("menuId",menuIds);
        }
        int row = userRoleMapper.deleteByExample(example);
        return Optional.of(row>0);
    }

    @Override
    public Optional<List<Long>> getRoleMenuByRoleId(Long roleId) {
        if (Objects.isNull(roleId)){
            return Optional.of(new ArrayList<>());
        }
        Example example = new Example(RoleMenu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId",roleId);

        List<Long> menuIds = roleMenuMapper.selectByExample(example).stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        return Optional.of(menuIds);
    }
}
