package io.ron.demo.jwt.starter.config;

import io.ron.jwt.starter.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
@Order(-1)
public class MyInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtXxxInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public JwtInterceptor jwtXxxInterceptor() {
        return new JwtAuthInterceptor();
    }

}
