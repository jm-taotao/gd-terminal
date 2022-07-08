package com.terminal.manage.controller;

import com.terminal.manage.base.config.ConfigModel;
import com.terminal.manage.base.enums.Constants;
import com.terminal.manage.base.excption.BizException;
import com.terminal.manage.base.response.Response;
import com.terminal.manage.model.User;
import com.terminal.manage.services.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author TAO
 * @date 2022/7/7 / 15:58
 */
@CrossOrigin
@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;


    @SuppressWarnings("unchecked")
    @RequestMapping("login")
    @ApiOperation(value = "登录", notes = "登录",response = User.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account",required = true,paramType = "String"),
            @ApiImplicitParam(name = "password",required = true,paramType = "String"),
    })
    public Response<User> login(String account, String password, HttpServletRequest request){
        Optional<User> optionalUsers = userService.login(account,password);

        optionalUsers.ifPresent(user ->{
            String token = ConfigModel.TOKEN_PREFIX+request.getSession().getId();
            redisTemplate.opsForValue().set(token, user, 60 * 1000 * 5, TimeUnit.MILLISECONDS);
        });
        return Response.doResponse(()->{
            return optionalUsers.orElse(optionalUsers.orElseThrow(()->{
                return new BizException(Constants.LOGIN_FAILED);
            }));
        });
    }
}
