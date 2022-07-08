package com.terminal.manage.base.response;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author Jyt
 * @date 2021/9/29
 */

public class Response<T> {

    private final static Logger log = LoggerFactory.getLogger(Response.class);

    private T data;

    private String code;

    private Boolean success;

    private String msg;

    /**
     * Factory method for return
     * @param func
     * @param <T>
     * @return
     */
    public static <T> Response<T> doResponse(Supplier<T> func){
        Response<T> resp = new Response<>();
        try {
            T data = func.get();
            resp.setData(data);
            resp.setCode("100000");
            resp.setMsg("");
            resp.setSuccess(true);
        }catch (Exception e){
            log.info(e.getMessage(),e);
            resp.setCode("400000");
            resp.setMsg(e.getMessage());
            resp.setSuccess(false);
        }
        return resp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
