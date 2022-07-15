package com.terminal.manage.controller;

import com.terminal.manage.base.config.ConfigModel;
import com.terminal.manage.base.enums.Constants;
import com.terminal.manage.base.excption.BizException;
import com.terminal.manage.base.response.Response;
import com.terminal.manage.model.Menu;
import com.terminal.manage.model.User;
import com.terminal.manage.services.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author Jyt
 * @date 2021/9/30
 */
@CrossOrigin
@RestController
@RequestMapping("/menu/")
public class MenuController {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private MenuService menuService;


    @SuppressWarnings("unchecked")
    @RequestMapping("tree")
    @ApiOperation(value = "获取树形菜单", notes = "获取树形菜单",response = User.class,httpMethod = "POST")
    public Response<List<Menu>> list(Menu menu){

        String menuKey = ConfigModel.KEY_PREFIX+"menu";
        List<Menu> arrays = (List<Menu>) redisTemplate.opsForValue().get(menuKey);
        if (!CollectionUtils.isEmpty(arrays)){
            Response.doResponse(arrays);
        }
        return Response.doResponse(()->{
            Optional<List<Menu>> menuTree = menuService.getMenuTree(menu);
            menuTree.ifPresent(menuList -> redisTemplate.opsForValue().set(menuKey, menuList));
            return menuTree.orElseGet(ArrayList::new);
        });
    }



    @SuppressWarnings("unchecked")
    @RequestMapping("treeForLabel")
    @ApiOperation(value = "获取树形菜单", notes = "获取树形菜单",response = User.class,httpMethod = "POST")
    public Response<List<HashMap<String, Object>>> menuTreeForLabel(Menu menu){

        String menuKey = ConfigModel.KEY_PREFIX+"menuForLabel";
        List<Menu> arrays = (List<Menu>) redisTemplate.opsForValue().get(menuKey);
        if (!CollectionUtils.isEmpty(arrays)){
            Response.doResponse(arrays);
        }
        return Response.doResponse(()->{
            Optional<List<HashMap<String, Object>>> menuTree = menuService.getMenuTreeForLabel(menu);
            menuTree.ifPresent(menuList -> redisTemplate.opsForValue().set(menuKey, menuList));
            return menuTree.orElseThrow(()->{
                return new BizException(Constants.GET_MENU_FAILED);
            });
        });
    }


    @RequestMapping("detail")
    public Response<Menu> detail(Long menuId){
        return Response.doResponse(()->{
            Optional<Menu> menuOptional = menuService.getMenuById(menuId);
            return menuOptional.orElse(null);
        });
    }


    @RequestMapping("add")
    public Response<Boolean> add(Menu menu){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = menuService.addMenu(menu);
            return optionalBoolean.orElse(false);
        });
    }

    @RequestMapping("update")
    public Response<Boolean> update(Menu menu){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = menuService.updateMenu(menu);
            return optionalBoolean.orElse(false);
        });
    }


    @RequestMapping("del")
    public Response<Boolean> delete(Long menuId){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = menuService.delMenuById(menuId);
            return optionalBoolean.orElse(false);
        });
    }

}
