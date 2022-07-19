package com.terminal.manage.controller;

import com.github.pagehelper.PageInfo;
import com.terminal.manage.base.response.Response;
import com.terminal.manage.model.Role;
import com.terminal.manage.services.MenuService;
import com.terminal.manage.services.RoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TAO
 * @date 2022/7/16 / 14:33
 */
@RestController
@RequestMapping("/role/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @RequestMapping("list")
    @ApiOperation(value = "角色列表", notes = "角色列表",response = Role.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",required = true,paramType = "Integer"),
            @ApiImplicitParam(name = "pageSize",required = true,paramType = "Integer"),
            @ApiImplicitParam(name = "role",required = true,paramType = "Role")
    })
    public Response<PageInfo<Role>> list(Integer page, Integer pageSize, Role role){
        return Response.doResponse(()->{
            Optional<PageInfo<Role>> optionalRoles = roleService.pageRoleList(page-1, pageSize, role);
            return optionalRoles.orElse(null);
        });
    }

    @ApiOperation(value = "角色信息", notes = "角色信息",response = Role.class,httpMethod = "POST")
    @ApiImplicitParam(name = "roleId",required = true,paramType = "Long")
    @RequestMapping("detail")
    public Response<Role> detail(Long roleId){
        return Response.doResponse(()->{
            Optional<Role> role = roleService.getRoleById(roleId);
            return role.orElse(null);
        });
    }

    @ApiOperation(value = "新增角色", notes = "新增角色",response = Boolean.class,httpMethod = "POST")
    @ApiImplicitParam(name = "role",required = true,paramType = "Role")
    @RequestMapping("add")
    public Response<Boolean> add(Role role){
        return Response.doResponse(()->{
            Optional<Boolean> booleanOptional = roleService.addRole(role);
            return booleanOptional.orElse(false);
        });
    }

    @RequestMapping("update")
    public Response<Boolean> update(Role role){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = roleService.updateRole(role);
            return optionalBoolean.orElse(false);
        });
    }

    @RequestMapping("del")
    public Response<Boolean> delRoleById(Long id){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = roleService.delRoleById(id);
            return optionalBoolean.orElse(false);
        });
    }

    @RequestMapping("addUserRole")
    public Response<Boolean> addUserRole(Long userId, List<Long> roleIds){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = roleService.addUserRole(userId,roleIds);
            return optionalBoolean.orElse(false);
        });
    }

    @RequestMapping("removeUserRole")
    public Response<Boolean> removeUserRole(Long userId, List<Long> roleIds){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = roleService.removeUserRole(userId,roleIds);
            return optionalBoolean.orElse(false);
        });
    }


    @RequestMapping("addRoleMenus")
    public Response<Boolean> addRoleMenus(Long roleId, String menuIdStr){
        if (StringUtils.isEmpty(menuIdStr)) {
            return Response.doResponse(false);
        }
//        if (!menuIdStr.contains(",")) {
//            return Response.doResponse(false);
//        }
        String[] menuIds = menuIdStr.split(",");
        if (menuIds.length<=0){
            return Response.doResponse(false);
        }
        List<Long> menuIdList = Arrays.stream(menuIds).map(Long::parseLong).collect(Collectors.toList());
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = roleService.addRoleMenu(roleId,menuIdList);
            return optionalBoolean.orElse(false);
        });
    }

    @RequestMapping("roleMenuList")
    public Response<List<Long>> getRoleMenuIds(Long roleId){
        return Response.doResponse(()->{
            Optional<List<Long>> optionalBoolean = roleService.getRoleMenuByRoleId(roleId);
            return optionalBoolean.orElse(new ArrayList<>());
        });
    }
    
}
