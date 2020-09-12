package io.ron.jwt.starter;

import io.ron.jwt.JwtContext;
import io.ron.jwt.annotation.AuthRequired;
import io.ron.jwt.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class JwtInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    private static final String PREFIX_BEARER = "Bearer ";

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 检查是否有 @AuthRequired 注解，有且 required() 为 false 则跳过
        if (method.isAnnotationPresent(AuthRequired.class)) {
            AuthRequired authRequired = method.getAnnotation(AuthRequired.class);
            if (!authRequired.required()) {
                return true;
            }
        }

        String token = request.getHeader(jwtProperties.getTokenName());

        logger.info("token: {}", token);

        if (StringUtils.isEmpty(token) || token.trim().equals(PREFIX_BEARER.trim())) {
            return true;
        }

        token = token.replace(PREFIX_BEARER, "");

        // 设置线程局部变量中的 token
        JwtContext.setToken(token);

        // 在线程局部变量中设置真实传递的数据，如当前用户信息等
        String payload = jwtService.verify(token);
        JwtContext.setPayload(payload);

        return onPreHandleEnd(request, response, handler, payload);
    }

    public boolean onPreHandleEnd(HttpServletRequest request, HttpServletResponse response,
                                  Object handler, String payload) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        // 务必在线程结束前清理线程局部变量
        JwtContext.removeAll();
    }
}
