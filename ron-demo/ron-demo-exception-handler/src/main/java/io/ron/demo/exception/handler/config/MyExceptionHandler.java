package io.ron.demo.exception.handler.config;

import io.ron.common.web.GlobalExceptionHandler;
import io.ron.demo.exception.handler.ExceptionHandlerApplication;
import org.springframework.context.annotation.Configuration;

/**
 * {@link ExceptionHandlerApplication} 中未扫描 {@link GlobalExceptionHandler}
 *
 * 这里通过继承来添加全局异常处理类
 */
@Configuration
public class MyExceptionHandler extends GlobalExceptionHandler {

}
