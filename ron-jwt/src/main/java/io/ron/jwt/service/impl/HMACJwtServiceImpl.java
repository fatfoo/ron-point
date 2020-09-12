package io.ron.jwt.service.impl;

import io.ron.jwt.JwtConfig;
import io.ron.jwt.exception.KeyGenerateException;
import io.ron.jwt.service.JwtService;
import io.ron.jwt.JwtUtils;

public class HMACJwtServiceImpl implements JwtService {

    private JwtConfig jwtConfig;

    public HMACJwtServiceImpl(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public String genKey() {
        String key = jwtConfig.getHmacKey();
        if (JwtUtils.isEmpty(key)) {
            throw new KeyGenerateException(JwtUtils.KEY_GEN_ERROR, new NullPointerException("HMAC need a key"));
        }
        return key;
    }

    @Override
    public String sign(String info) {
        return JwtUtils.signClaimByHMAC(info, genKey(), jwtConfig);
    }

    @Override
    public String verify(String token) {
        return JwtUtils.verifyClaimByHMAC(token, genKey(), jwtConfig);
    }
}
