package com.iflove.utils;

import com.iflove.entity.RestBean;
import com.iflove.entity.ResultCodeEnum;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 封装两种消息处理方法
 * stringMessageHandle处理返回值为string作为错误信息的方法，返回值为null代表成功
 * messageHandle处理返回值为任意类型的方法，返回值为null代表失败
 */
public class MessageHandler {
    /**
     * 针对于传入值为 VO对象 或 单变量 且返回值为String作为错误信息的方法进行统一处理
     * @param vo 数据封装对象
     * @param function 调用service方法
     * @return 响应结果
     * @param <T> 响应结果类型
     */
    public static <T> RestBean<Void> stringMessageHandle(T vo, Function<T, String> function) {
        return stringMessageHandle(() -> function.apply(vo));
    }

    /**
     * 针对于返回值为String作为错误信息的方法进行统一处理
     * @param action 具体操作
     * @return 响应结果
     * @param <T> 响应结果类型
     */
    public static <T> RestBean<T> stringMessageHandle(Supplier<String> action) {
        String message = action.get();
        return Objects.isNull(message) ? RestBean.success() : RestBean.failure(400, message);
    }

    /**
     * 针对于传入值为 VO对象 或 单变量 且返回值为任意类型数据的方法进行统一处理
     * @param vo 数据封装对象
     * @param function 调用service方法
     * @return 响应结果
     * @param <T> 数据封装对象类型
     * @param <R> 返回值类型
     */
    public static <T, R> RestBean<R> messageHandle(T vo, Function<T, R> function) {
        return messageHandle(() -> function.apply(vo));
    }

    /**
     * 针对于返回值为任意类型数据的方法进行统一处理
     * @param action 具体操作
     * @return 响应结果
     * @param <R> 返回值类型
     */
    public static <R> RestBean<R> messageHandle(Supplier<R> action) {
        R data = action.get();
        return Objects.nonNull(data) ? RestBean.success(data) : RestBean.failure(ResultCodeEnum.ERROR);
    }
}
