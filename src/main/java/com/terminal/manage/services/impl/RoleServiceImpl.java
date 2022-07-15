package com.terminal.manage.services.impl;

import com.github.pagehelper.PageInfo;
import com.terminal.manage.base.enums.IsDeleted;
import com.terminal.manage.mapper.RoleMapper;
import com.terminal.manage.model.Role;
import com.terminal.manage.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author TAO
 * @date 2022/7/16 / 2:12
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public Optional<Boolean> addRole(Role role) {

        role.setIsDeleted(IsDeleted.NO.code);
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        int row = roleMapper.insertUseGeneratedKeys(role);
        return Optional.of(row>0);
    }

    @Override
    public Optional<Boolean> updateRole(Role role) {
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> delRoleById(Long roleId) {
        return Optional.empty();
    }

    @Override
    public Optional<PageInfo<Role>> pageRoleList(Integer page, Integer pageSize, Role role) {
        return Optional.empty();
    }

    @Override
    public Optional<Role> getRoleById(Long roleId) {
        return Optional.empty();
    }
}
