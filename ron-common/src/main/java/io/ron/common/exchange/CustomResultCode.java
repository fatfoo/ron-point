package io.ron.common.exchange;

import io.ron.common.web.GlobalExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于标识实体中需要校验的字段
 *
 * 当校验结果仅包含 1 个异常时根据该注解设置响应编码
 *
 * 详见方法 {@link GlobalExceptionHandler#methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException)} 的处理逻辑
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CustomResultCode {

    String value() default "OK";

    Class type() default CommonResultCode.class;
}
