package io.ron.demo.jwt.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ron.demo.jwt.starter.dto.UserDTO;
import io.ron.jwt.JwtContext;
import io.ron.jwt.starter.JwtInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthInterceptor extends JwtInterceptor {

    private Logger logger = LoggerFactory.getLogger(JwtAuthInterceptor.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean onPreHandleEnd(HttpServletRequest request, HttpServletResponse response,
                                  Object handler, String payload) throws Exception {
        super.onPreHandleEnd(request, response, handler, payload);

        UserDTO user = objectMapper.readValue(payload, UserDTO.class);

        // 设置当前登录用户
        JwtContext.setPayload(user);

        logger.info("current user: {}", user.toString());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        // 调用父类中清理线程局部变量的方法
        super.afterCompletion(request, response, handler, ex);
    }
}
