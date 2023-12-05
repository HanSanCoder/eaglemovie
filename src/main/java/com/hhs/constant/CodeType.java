package com.hhs.constant;

/**
 * @author ：何汉叁
 * @date ：2023/11/24 21:34
 * @description：TODO
 */
public enum CodeType {
    SUCCESS_MSG("操作成功"),
    SUCCESS_CODE(0),

    // 登录失败
    LOGIN_ERROR_CODE(1301),
    LOGIN_ERROR_MSG("fail"),

    //管理员和用户登录
    ADMIN_MSG("adminsuccess"),
    USER_MSG("usersuccess"),

    ERROR_CODE(200);
    private final String message;
    private final int code;
    CodeType(String message) {
        this.code = 0;
        this.message = message;
    }

    CodeType(int code) {
        this.code = code;
        this.message = null;
    }
}
