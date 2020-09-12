package io.ron.jwt.service.impl;

import com.nimbusds.jose.jwk.RSAKey;
import io.ron.jwt.JwtConfig;
import io.ron.jwt.JwtUtils;
import io.ron.jwt.exception.KeyGenerateException;
import io.ron.jwt.service.JwtService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;

public class RSAJwtServiceImpl implements JwtService {

    private JwtConfig jwtConfig;

    private RSAKey rsaKey;

    public RSAJwtServiceImpl(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    private InputStream getCertInputStream() throws IOException {
        // 读取配置文件中的证书路径
        String jksFile = jwtConfig.getJksFileName();
        if (jksFile.contains("://")) {
            // 从本地文件读取
            return new FileInputStream(new File(jksFile));
        } else {
            // 从 classpath 读取
            return getClass().getClassLoader().getResourceAsStream(jwtConfig.getJksFileName());
        }
    }

    @Override
    public RSAKey genKey() {
        if (rsaKey != null) {
            return rsaKey;
        }
        InputStream is = null;
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            is = getCertInputStream();
            keyStore.load(is, jwtConfig.getJksPassword().toCharArray());
            Enumeration<String> aliases = keyStore.aliases();
            String alias = null;
            while (aliases.hasMoreElements()) {
                alias = aliases.nextElement();
            }
            RSAPrivateKey privateKey = (RSAPrivateKey) keyStore.getKey(alias, jwtConfig.getCertPassword().toCharArray());
            Certificate certificate = keyStore.getCertificate(alias);
            RSAPublicKey publicKey = (RSAPublicKey) certificate.getPublicKey();
            rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
            return rsaKey;
        } catch (IOException | CertificateException | UnrecoverableKeyException
                | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
            throw new KeyGenerateException(JwtUtils.KEY_GEN_ERROR, e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String sign(String payload) {
        return JwtUtils.signClaimByRSA(payload, genKey(), jwtConfig);
    }

    @Override
    public String verify(String token) {
        return JwtUtils.verifyClaimByRSA(token, genKey(), jwtConfig);
    }
}
