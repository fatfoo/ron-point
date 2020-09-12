package io.ron.jwt.starter;

import io.ron.jwt.JwtConfig;
import io.ron.jwt.JwtUtils;
import io.ron.jwt.service.JwtService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration {

    @Autowired
    private JwtProperties jwtProperties;

    @Bean
    public JwtConfig jwtConfig() {
        JwtConfig jwtConfig = new JwtConfig();
        BeanUtils.copyProperties(jwtProperties, jwtConfig);
        return jwtConfig;
    }

    @Bean
    public JwtService jwtService() {
        JwtConfig jwtConfig = jwtConfig();
        return JwtUtils.obtainJwtService(jwtConfig);
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    @Bean
    public JwtInterceptorConfig jwtInterceptorConfig() {
        return new JwtInterceptorConfig(jwtInterceptor());
    }
}
