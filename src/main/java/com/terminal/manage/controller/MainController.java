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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;
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
    public Response<User> login(String account, String password, HttpServletRequest request){

        log.info("用户:{} 登录时间 {}", account,LocalDateTime.now());
        String token = ConfigModel.TOKEN_PREFIX+request.getSession().getId();
        User o = (User)redisTemplate.opsForValue().get(token);
        if(Objects.nonNull(o)){
            return Response.doResponse(o);
        }
        Optional<User> optionalUsers = userService.login(account,password);
        optionalUsers.ifPresent(user ->{
            redisTemplate.opsForValue().set(token, user, 60 * 1000 * 5, TimeUnit.MILLISECONDS);
        });
        return Response.doResponse(()->{
            return optionalUsers.orElse(optionalUsers.orElseThrow(()->{
                return new BizException(Constants.LOGIN_FAILED);
            }));
        });
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
