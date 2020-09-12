package io.ron.jwt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 应用于 Controller 中的方法，标识是否拦截进行 JWT 验证
 */
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AuthRequired {

    boolean required() default true;
}
