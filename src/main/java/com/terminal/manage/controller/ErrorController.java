package com.terminal.manage.controller;

import com.terminal.manage.base.excption.BizException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TAO
 * @date 2022/7/17 / 1:20
 */
@RestController
@RequestMapping("/error/")
public class ErrorController {

    @RequestMapping("throwError")
    public void throwError(String code, String msg){

        throw new BizException(code,msg);
    }


}
