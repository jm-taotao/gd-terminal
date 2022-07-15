package com.terminal.manage.services;

import com.github.pagehelper.PageInfo;
import com.terminal.manage.model.Role;

import java.util.Optional;

/**
 * @author TAO
 * @date 2022/7/16 / 2:11
 */
public interface RoleService {

    Optional<Boolean> addRole(Role role);

    Optional<Boolean> updateRole(Role role);

    Optional<Boolean> delRoleById(Long roleId);

    Optional<PageInfo<Role>> pageRoleList(Integer page, Integer pageSize, Role role);

    Optional<Role> getRoleById(Long roleId);

}
