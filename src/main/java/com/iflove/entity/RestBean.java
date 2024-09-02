package com.iflove.entity;


/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 返回结果集
 */
public record RestBean<T> (int code, T data, String message) {

    public static <T> RestBean<T> build(T body, ResultCodeEnum resultCodeEnum) {
        return new RestBean<>(resultCodeEnum.getCode(), body, resultCodeEnum.getMessage());
    }

    public static <T> RestBean<T> success(T data) {
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static <T> RestBean<T> success() {
        return build(null, ResultCodeEnum.SUCCESS);
    }

    public static <T> RestBean<T> failure(int code, String message) {
        return new RestBean<>(code, null, message);
    }

    public static <T> RestBean<T> failure(ResultCodeEnum resultCodeEnum) {
        return build(null, resultCodeEnum);
    }

}
