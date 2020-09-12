package io.ron.demo.jwt.config;

import io.ron.jwt.JwtConfig;
import io.ron.jwt.JwtUtils;
import io.ron.jwt.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtDemoInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public JwtDemoInterceptor jwtDemoInterceptor() {
        return new JwtDemoInterceptor();
    }

    @Bean
    public JwtConfig jwtDemoConfig() {
        JwtConfig jwtConfig = new JwtConfig();
//        jwtConfig.setHmacKey("cb9915297c8b43e820afd2a90a1e36cb");

        jwtConfig.setJksFileName("jwt.jks");
        jwtConfig.setJksPassword("ronjwt");
        jwtConfig.setCertPassword("ronjwt");
        return jwtConfig;
    }

    @Bean
    public JwtService jwtDemoService() {
        return JwtUtils.obtainJwtService(jwtDemoConfig());
    }

}
