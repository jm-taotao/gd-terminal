package com.terminal.manage.controller;

import com.terminal.manage.base.response.Response;
import com.terminal.manage.model.Menu;
import com.terminal.manage.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author Jyt
 * @date 2021/9/30
 */
@RestController
@RequestMapping("/menu/")
public class MenuController {


    @Autowired
    private MenuService menuService;


    @RequestMapping("list")
    public Response<List<Menu>> list(){
        return null;
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


    @RequestMapping("delete")
    public Response<Boolean> delete(Long menuId){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = menuService.delMenuById(menuId);
            return optionalBoolean.orElse(false);
        });
    }

}
