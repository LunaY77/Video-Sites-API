package com.iflove.entity;

import lombok.Getter;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(200,"success"),
    UNKNOWN(500, "未知的服务器错误"),
    INTERNAL(500, "内部服务错误"),
    UNAUTHENTICATED(501, "用户未登录"),
    INVALID_PARAMETER(503, "请求参数错误"),
    EMAIL_UN_USED(504,"该邮箱未被注册"),
    FORBIDDEN(505, "没有权限访问"),
    FREQUENT_OPERATION(506, "操作频繁, 请稍后再试"),
    WRONG_USERNAME_OR_PASSWORD(507, "用户名或密码错误");

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
