package io.ron.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    /**
     * 自定义 {@link ObjectMapper} 对象。
     *
     * @param builder
     * @return
     */
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {

        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        /*
         * 设置对象的序列化规则
         *
         * JsonInclude.Include.ALWAYS: 序列化所有字段
         * JsonInclude.Include.NON_DEFAULT: 属性的默认值不序列化
         * JsonInclude.Include.NON_EMPTY: 属性值为空("")或者为 NULL 都不序列化，返回的 json 中不包含改字段
         * JsonInclude.Include.NON_NULL: 属性值为 NULL 的字段不序列化
         *
         * 可以修改下方参数观察响应结果。
         */
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        return objectMapper;
    }
}