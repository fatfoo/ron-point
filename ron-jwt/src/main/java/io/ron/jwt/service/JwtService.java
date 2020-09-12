package io.ron.jwt.service;

public interface JwtService {

    /**
     * 获取 key
     *
     * @return
     */
    Object genKey();

    /**
     * 对信息进行签名
     *
     * @param payload
     * @return
     */
    String sign(String payload);

    /**
     * 验证并返回信息
     *
     * @param token
     * @return
     */
    String verify(String token);

}
