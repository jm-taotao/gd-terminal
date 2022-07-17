package com.terminal.manage.base.excption;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @author TAO
 * @date 2022/7/7 / 16:16
 */
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BizException.class)
    public HashMap<String,Object> handlerBiz(BizException biz){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("code",biz.getCode());
        hashMap.put("success",false);
        hashMap.put("msg",biz.getMsg());
        return hashMap;
    }

    @ExceptionHandler(Exception.class)
    public HashMap<String,Object> handlerBiz(Exception biz){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("code","50000");
        hashMap.put("success",false);
        hashMap.put("msg",biz.getMessage());
        return hashMap;
    }


}
