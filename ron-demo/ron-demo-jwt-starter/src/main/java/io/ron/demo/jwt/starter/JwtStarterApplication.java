package io.ron.demo.jwt.starter;

import io.ron.jwt.starter.EnableJwt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableJwt
@SpringBootApplication
public class JwtStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtStarterApplication.class, args);
    }
}
