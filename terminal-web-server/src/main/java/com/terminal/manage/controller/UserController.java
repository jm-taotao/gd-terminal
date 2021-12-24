package com.terminal.manage.controller;

import com.github.pagehelper.PageInfo;
import com.terminal.manage.base.response.Response;
import com.terminal.manage.model.User;
import com.terminal.manage.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Jyt
 * @date 2021/9/29
 */
@Api("用户管理")
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("login")
    @ApiOperation(value = "登录", notes = "登录",response = User.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account",required = true,paramType = "String"),
            @ApiImplicitParam(name = "password",required = true,paramType = "String"),
    })
    public Response<User> login(String account, String password){
        return Response.doResponse(()->{
            Optional<User> optionalUsers = userService.login(account,password);
            return optionalUsers.orElse(null);
        });
    }

    @RequestMapping("list")
    @ApiOperation(value = "用户列表", notes = "用户列表",response = User.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",required = true,paramType = "Integer"),
            @ApiImplicitParam(name = "pageSize",required = true,paramType = "Integer"),
            @ApiImplicitParam(name = "user",required = true,paramType = "User")
    })
    public Response<PageInfo<User>> list(Integer page, Integer pageSize, User user){
        return Response.doResponse(()->{
            Optional<PageInfo<User>> optionalUsers = userService.pageUserList(page, pageSize, user);
            return optionalUsers.orElse(null);
        });
    }

    @ApiOperation(value = "用户信息", notes = "用户信息",response = User.class,httpMethod = "POST")
    @ApiImplicitParam(name = "userId",required = true,paramType = "Long")
    @RequestMapping("detail")
    public Response<User> detail(Long userId){
        return Response.doResponse(()->{
           Optional<User> user = userService.getUserById(userId);
           return user.orElse(null);
        });
    }


    @RequestMapping("add")
    public Response<Boolean> add(User user){
        return Response.doResponse(()->{
            Optional<Boolean> booleanOptional = userService.addUser(user);
            return booleanOptional.orElse(false);
        });
    }

    @RequestMapping("update")
    public Response<Boolean> update(User user){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = userService.updateUser(user);
            return optionalBoolean.orElse(false);
        });
    }

    @RequestMapping("del")
    public Response<Boolean> delUserById(Long userId){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = userService.delUserById(userId);
            return optionalBoolean.orElse(false);
        });
    }
}
