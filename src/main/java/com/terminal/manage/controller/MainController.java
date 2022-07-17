package com.terminal.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.terminal.manage.base.config.ConfigModel;
import com.terminal.manage.base.enums.Constants;
import com.terminal.manage.base.excption.BizException;
import com.terminal.manage.base.response.Response;
import com.terminal.manage.model.User;
import com.terminal.manage.services.UserService;
import com.terminal.manage.tool.Encryption;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author TAO
 * @date 2022/7/7 / 15:58
 */
@RestController
@RequestMapping("/")
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);
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
    public Response<JSONObject> login(String account, String password, HttpServletRequest request){
        log.info("用户:{} 登录时间 {}", account,LocalDateTime.now());
        JSONObject result = new JSONObject();
        String tokenPrefix = ConfigModel.TOKEN_PREFIX+request.getSession().getId();
        String token = Encryption.getInstance().makeToken(tokenPrefix);
        result.put("token",token);
        User o = (User)redisTemplate.opsForValue().get(token);
        if(Objects.nonNull(o)){
            result.put("user",o);
            return Response.doResponse(result);
        }
        Optional<User> optionalUsers = userService.login(account,password);
        optionalUsers.ifPresent(user ->{
            ConcurrentHashMap<String,Object> sessionUser = new ConcurrentHashMap<>();
            sessionUser.put(token,user);
            result.put("user",user);
            redisTemplate.opsForValue().set(token, sessionUser, 60 * 1000 * 5, TimeUnit.MILLISECONDS);
        });
        return Response.doResponse(result);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("logOut")
    @ApiOperation(value = "退出登录", notes = "退出登录",response = User.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account",required = true,paramType = "String"),
            @ApiImplicitParam(name = "password",required = true,paramType = "String"),
    })
    public Response<Boolean> logOut(HttpServletRequest request){
        String token = ConfigModel.TOKEN_PREFIX+request.getSession().getId();
        User o = (User)redisTemplate.opsForValue().get(token);
        Boolean delete = redisTemplate.delete(token);
        if (Objects.nonNull(o)){
            log.info("用户:{} 登录时间 {}",o.getLoginName(),LocalDateTime.now());
        }
        return Response.doResponse(delete);
    }
}
