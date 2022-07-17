package com.terminal.manage.services;

import com.github.pagehelper.PageInfo;
import com.terminal.manage.model.Role;
import com.terminal.manage.model.RoleMenu;

import java.util.List;
import java.util.Optional;

/**
 * @author TAO
 * @date 2022/7/16 / 2:11
 */
public interface RoleService {

    /**
     * add role
     * @param role role
     * @return
     */
    Optional<Boolean> addRole(Role role);

    /**
     * update role
     * @param role role
     * @return
     */
    Optional<Boolean> updateRole(Role role);

    /**
     * del roleid with logic
     * @param roleId
     * @return
     */
    Optional<Boolean> delRoleById(Long roleId);

    /**
     * page role list
     * @param page
     * @param pageSize
     * @param role
     * @return
     */
    Optional<PageInfo<Role>> pageRoleList(Integer page, Integer pageSize, Role role);

    /**
     * get role info
     * @param roleId
     * @return
     */
    Optional<Role> getRoleById(Long roleId);

    /**
     * add user role
     * @param userId
     * @param roleIds
     * @return
     */
    Optional<Boolean> addUserRole(Long userId, List<Long> roleIds);


    /**
     * remove user role
     * @param userId
     * @param roleIds
     * @return
     */
    Optional<Boolean> removeUserRole(Long userId, List<Long> roleIds);


    /**
     * add role menus
     * @param roleId
     * @param menuIds
     * @return
     */
    Optional<Boolean> addRoleMenu(Long roleId, List<Long> menuIds);


    /**
     * remove role menus
     * @param roleId
     * @param menuIds
     * @return
     */
    Optional<Boolean> removeRoleMenu(Long roleId, List<Long> menuIds);


    /**
     * get role menus
     * @param roleId
     * @return menu id list
     */
    Optional<List<Long>> getRoleMenuByRoleId(Long roleId);



}
