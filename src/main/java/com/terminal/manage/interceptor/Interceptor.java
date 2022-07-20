package com.terminal.manage.interceptor;

import com.terminal.manage.base.enums.Constants;
import com.terminal.manage.base.excption.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
@Component
public class Interceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(Interceptor.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("请求地址 => {}",request.getRequestURL());
        if ("OPTIONS".equals(request.getMethod())){
            return true;
        }
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            throw new BizException("token为空");
        }
        Object o = redisTemplate.opsForValue().get(token);
        if (Objects.isNull(o)){
            response.setCharacterEncoding("UTF-8");
//            response.setContentType("");
//            String message = URLEncoder.encode(Constants.LOGIN_EXPIRE.msg, "UTF-8");
//            response.sendRedirect("/error/throwError?code="+Constants.LOGIN_EXPIRE.code+"&msg="+message);
            throw new BizException(Constants.LOGIN_EXPIRE);
//            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
