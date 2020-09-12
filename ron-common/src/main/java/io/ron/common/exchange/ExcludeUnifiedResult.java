package io.ron.common.exchange;

import io.ron.common.web.UnifiedResultAdvice;
import org.springframework.core.MethodParameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于 Controller 中方法的标注
 *
 * 该注解标注的方法将不返回统一响应结构
 *
 * {@link Result}
 * {@link UnifiedResultAdvice#supports(MethodParameter, Class)}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ExcludeUnifiedResult {

}
