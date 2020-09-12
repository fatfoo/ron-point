package io.ron.demo.jwt.config;

import io.ron.jwt.JwtConfig;
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

public class JwtDemoInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(JwtDemoInterceptor.class);

    private static final String PREFIX_BEARER = "Bearer ";

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtService jwtService;

    /**
     * 预处理回调方法，实现处理器的预处理（如检查登陆），第三个参数为响应的处理器，自定义 Controller
     * 返回值：
     * true 表示继续流程（如调用下一个拦截器或处理器）；
     * false 表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过 response 来产生响应。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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

        String token = request.getHeader(jwtConfig.getTokenName());

        logger.info("token: {}", token);

        if (StringUtils.isEmpty(token) || token.trim().equals(PREFIX_BEARER.trim())) {
            return true;
        }

        token = token.replace(PREFIX_BEARER, "");

        String payload = jwtService.verify(token);

        // 设置线程局部变量中的 token
        JwtContext.setToken(token);
        JwtContext.setPayload(payload);
        return true;
    }

    /**
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前），此时我们可以通过 modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView 也可能为null。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理，类似于 try-catch-finally 中的 finally
     * 但仅调用处理器执行链中 preHandle 返回 true 的拦截器的 afterCompletion。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        JwtContext.removeAll();
    }
}
