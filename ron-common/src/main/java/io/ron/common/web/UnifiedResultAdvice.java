package io.ron.common.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ron.common.exchange.ApiException;
import io.ron.common.exchange.ExcludeUnifiedResult;
import io.ron.common.exchange.Result;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一响应结构包装
 */
@ConditionalOnProperty(prefix = "ron.unified-result", value = "enabled", havingValue = "true")
@RestControllerAdvice
public class UnifiedResultAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return !methodParameter.getParameterType().equals(Result.class) &&
                !methodParameter.hasMethodAnnotation(ExcludeUnifiedResult.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (methodParameter.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在 Result 里后，再转换为 json 字符串响应给前端
                return objectMapper.writeValueAsString(Result.ok(o));
            } catch (JsonProcessingException e) {
                throw new ApiException("返回 String 类型错误");
            }
        }

        return Result.ok(o);
    }
}
