package com.terminal.manage.base.excption;

import com.terminal.manage.base.enums.Constants;

/**
 * @author TAO
 * @date 2022/7/7 / 16:15
 */
public class BizException extends RuntimeException{

    private static final long serialVersionUID = -7730831150878342205L;

    private String code;

    private String msg;

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
        this.msg = message;
    }
    public BizException(String code,String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BizException(Constants constants) {
        super(constants.msg);
        this.code = constants.code;
        this.msg = constants.msg;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
