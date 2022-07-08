package com.terminal.manage.base.enums;

/**
 * @author Jyt
 * @date 2021/9/30
 */
public enum IsDeleted {

    YES("Y","是"),
    NO("N","否")

    ;

    public final String code;

    public final String desc;

    IsDeleted(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
