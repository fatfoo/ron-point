package io.ron.demo.jwt.starter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ron.demo.jwt.starter.dto.UserDTO;
import io.ron.jwt.JwtContext;
import io.ron.jwt.annotation.AuthRequired;
import io.ron.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @AuthRequired(required = false)
    @GetMapping("/sign")
    public String sign() throws JsonProcessingException {

        UserDTO userDTO = new UserDTO();
        userDTO.setName("fatfoo");
        userDTO.setPassword("112233");
        userDTO.setSex(0);

        String payload = objectMapper.writeValueAsString(userDTO);

        return jwtService.sign(payload);
    }

    @GetMapping("/verify")
    public UserDTO verify() throws IOException {
        String payload = (String) JwtContext.getPayload();
        return objectMapper.readValue(payload, UserDTO.class);
//        return (UserDTO) JwtContext.getPayload();
    }
}
