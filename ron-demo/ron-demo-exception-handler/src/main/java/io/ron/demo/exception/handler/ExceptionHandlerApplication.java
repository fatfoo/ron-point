package io.ron.demo.exception.handler;

import io.ron.common.web.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 默认扫描当前类所在包及子包下的 Bean，故不会扫描到 common 中的 {@link GlobalExceptionHandler}
 */
@SpringBootApplication
public class ExceptionHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExceptionHandlerApplication.class, args);
    }
}
