package com.terminal.manage.base.enums;

/**
 * @author TAO
 * @date 2022/7/7 / 16:19
 */
public enum Constants {

    LOGIN_SUCCESS("10000","登录成功"),
    LOGIN_FAILED("10001","登录失败"),
    LOGIN_FAILED_NOT_EXIST("10002","用户不存在"),
    LOGIN_FAILED_PASSWORD_MISTAKE("10003","密码错误"),
    GET_MENU_FAILED("10004","获取菜单异常"),
    USER_PASSWORD_ISEMPTY("10005","用户密码不能为空"),
    USER_EXIST("10006","用户名已存在"),
    MENU_EXIST("10007","菜单已存在"),
    MENU_EXIST_CHILDREN("10008","菜单存在子菜单,暂无法修改父级菜单"),
    ROLE_EXIST("10009","角色已存在"),
    LOGIN_EXPIRE("10010","登录已过期"),
    PRODUCT_EXIST("10011","商品已存在"),
    ;

    public final String code;
    public final String msg;

    Constants(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
