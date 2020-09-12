package io.ron.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.ron.jwt.exception.PayloadSignException;
import io.ron.jwt.exception.TokenVerifyException;
import io.ron.jwt.service.JwtService;
import io.ron.jwt.service.impl.HMACJwtServiceImpl;
import io.ron.jwt.service.impl.RSAJwtServiceImpl;

import java.text.ParseException;
import java.util.Date;

public class JwtUtils {

    public static final String CLAIM_PAYLOAD = "payload";

    public static final String KEY_GEN_ERROR = "Can not generate valid key";

    public static final String PAYLOAD_SIGN_ERROR = "Can not sign the payload";

    public static final String TOKEN_VERIFY_ERROR = "Invalid token";

    public static final String DEFAULT_TOKEN_NAME = "Authorization";

    private JwtUtils() {}

    public static void main(String[] args) throws Exception {

        // 定义需要传递的私有信息
        String info = "linfuyan@xiamen";

        // 设置 jwt 中的标准信息
        JwtConfig jwtConfig = new JwtConfig();
        jwtConfig.setExpiredIn(1L);
        jwtConfig.setNotBeforeIn(1L);

        // 用于 HMAC 的密钥
        String key = "b06fbd588d7385745b68037c40e50c2fb06fbd588d7385745b68037c40e50c2f";

        // 生成测试用 RSA 密钥，真实情况下会从证书文件读取
        RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator(2048);
        RSAKey rsaKey = rsaKeyGenerator.generate();

        // 测试 HMAC 签名与验证，仅包含私有信息
        String t = signDirectByHMAC(info, key);
        System.out.println("token:" + t);
        String i = verifyDirectByHMAC(t, key);
        System.out.println(i);

        // 测试 RSA 签名与验证，仅包含私有信息
        t = signDirectByRSA(info, rsaKey);
        System.out.println("token:" + t);
        i = verifyDirectByRSA(t, rsaKey);
        System.out.println(i);

        // 测试 HMAC 签名与验证，包含标准+私有信息
        t = signClaimByHMAC(info, key, jwtConfig);
        System.out.println("token:" + t);
        i = verifyDirectByHMAC(t, key);
        System.out.println(i);

        // 测试 RSA 签名与验证，包含标准+私有信息
        t = signClaimByRSA(info, rsaKey, jwtConfig);
        System.out.println("token:" + t);
        Thread.sleep(2000);
        i = verifyClaimByRSA(t, rsaKey, jwtConfig);
        System.out.println(i);
    }

    /**
     * 使用 HMAC 算法签名信息（Payload 中只包含私有信息）
     *
     * @param info
     * @param key
     * @return
     */
    public static String signDirectByHMAC(String info, String key) {
        try {
            JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256)
                    .type(JOSEObjectType.JWT)
                    .build();

            // 建立一个载荷 Payload
            Payload payload = new Payload(info);

            // 将头部和载荷结合在一起
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);

            // 建立一个密匙
            JWSSigner jwsSigner = new MACSigner(key);

            // 签名
            jwsObject.sign(jwsSigner);

            // 生成 token
            return jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            throw new PayloadSignException(JwtUtils.PAYLOAD_SIGN_ERROR, e);
        }
    }

    /**
     * 使用 RSA 算法签名信息（Payload 中只包含私有信息）
     *
     * @param info
     * @param rsaKey
     * @return
     */
    public static String signDirectByRSA(String info, RSAKey rsaKey) {
        try {
            JWSSigner signer = new RSASSASigner(rsaKey);
            JWSObject jwsObject = new JWSObject(
                    new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).build(),
                    new Payload(info)
            );
            // 进行加密
            jwsObject.sign(signer);

            return jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            throw new PayloadSignException(JwtUtils.PAYLOAD_SIGN_ERROR, e);
        }
    }

    /**
     * 使用 HMAC 算法验证 token（Payload 中只包含私有信息）
     *
     * @param token
     * @param key
     * @return
     */
    public static String verifyDirectByHMAC(String token, String key) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            // 建立一个解锁密匙
            JWSVerifier jwsVerifier = new MACVerifier(key);
            if (jwsObject.verify(jwsVerifier)) {
                return jwsObject.getPayload().toString();
            }
            throw new TokenVerifyException(JwtUtils.TOKEN_VERIFY_ERROR, new NullPointerException("Payload can not be null"));
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
            throw new TokenVerifyException(JwtUtils.TOKEN_VERIFY_ERROR, e);
        }
    }

    /**
     * 使用 RSA 算法验证 token（Payload 中只包含私有信息）
     *
     * @param token
     * @param rsaKey
     * @return
     */
    public static String verifyDirectByRSA(String token, RSAKey rsaKey) {
        try {
            RSAKey publicRSAKey = rsaKey.toPublicJWK();
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier jwsVerifier = new RSASSAVerifier(publicRSAKey);
            // 验证数据
            if (jwsObject.verify(jwsVerifier)) {
                return jwsObject.getPayload().toString();
            }
            throw new TokenVerifyException(JwtUtils.TOKEN_VERIFY_ERROR, new NullPointerException("Payload can not be null"));
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
            throw new TokenVerifyException(JwtUtils.TOKEN_VERIFY_ERROR, e);
        }
    }

    /**
     * 使用 HMAC 算法签名信息（Payload 中只包含标准信息与私有信息）
     *
     * @param info
     * @param key
     * @param jwtConfig
     * @return
     */
    public static String signClaimByHMAC(String info, String key, JwtConfig jwtConfig) {
        try {
            JWSSigner jwsSigner = new MACSigner(key);
            final JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
            SignedJWT signedJWT = new SignedJWT(header, buildClaim(info, jwtConfig));
            signedJWT.sign(jwsSigner);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            throw new PayloadSignException(JwtUtils.PAYLOAD_SIGN_ERROR, e);
        }
    }

    /**
     * 使用 RSA 算法签名信息（Payload 中只包含标准信息与私有信息）
     *
     * @param info
     * @param rsaKey
     * @param jwtConfig
     * @return
     */
    public static String signClaimByRSA(String info, RSAKey rsaKey, JwtConfig jwtConfig) {
        try {
            JWSSigner jwsSigner = new RSASSASigner(rsaKey);
            final JWSHeader header = new JWSHeader(JWSAlgorithm.RS256);
            SignedJWT signedJWT = new SignedJWT(header, buildClaim(info, jwtConfig));
            signedJWT.sign(jwsSigner);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            throw new PayloadSignException(JwtUtils.PAYLOAD_SIGN_ERROR, e);
        }
    }

    private static JWTClaimsSet buildClaim(String info, JwtConfig jwtConfig) {

        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        String subject = jwtConfig.getSubject();
        if (!JwtUtils.isEmpty(subject)) {
            builder.subject(subject);
        }

        String issuer = jwtConfig.getIssuer();
        if (!JwtUtils.isEmpty(issuer)) {
            builder.issuer(issuer);
        }

        Date now = new Date();
        builder.issueTime(now);

        long expiredIn = jwtConfig.getExpiredIn();
        if (expiredIn > 0) {
            Date expiredAt = new Date(new Date().getTime() + expiredIn * 1000);
            builder.expirationTime(expiredAt);
        }

        long notBeforeIn = jwtConfig.getNotBeforeIn();
        if (notBeforeIn > 0) {
            builder.notBeforeTime(new Date(now.getTime() + notBeforeIn * 1000));
        }

        builder.claim(JwtUtils.CLAIM_PAYLOAD, info);

        // 生成 token
        JWTClaimsSet claimsSet = builder.build();

        return claimsSet;
    }

    /**
     * 使用 HMAC 算法验证 token（Payload 中只包含标准信息与私有信息）
     *
     * @param token
     * @param key
     * @param jwtConfig
     * @return
     */
    public static String verifyClaimByHMAC(String token, String key, JwtConfig jwtConfig) {
        try {
            final SignedJWT parseJWT = SignedJWT.parse(token);
            JWSVerifier jwsVerifier = new MACVerifier(key);

            return verifyClaim(jwsVerifier, parseJWT, jwtConfig);
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
            throw new TokenVerifyException(JwtUtils.TOKEN_VERIFY_ERROR, e);
        }
    }

    /**
     * 使用 RSA 算法验证 token（Payload 中只包含标准信息与私有信息）
     *
     * @param token
     * @param rsaKey
     * @param jwtConfig
     * @return
     */
    public static String verifyClaimByRSA(String token, RSAKey rsaKey, JwtConfig jwtConfig) {
        try {
            final SignedJWT parseJWT = SignedJWT.parse(token);
            RSAKey publicRSAKey = rsaKey.toPublicJWK();
            JWSVerifier jwsVerifier = new RSASSAVerifier(publicRSAKey);

            return verifyClaim(jwsVerifier, parseJWT, jwtConfig);
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
            throw new TokenVerifyException(JwtUtils.TOKEN_VERIFY_ERROR, e);
        }
    }

    private static String verifyClaim(JWSVerifier jwsVerifier, SignedJWT parseJWT, JwtConfig jwtConfig)
            throws JOSEException, ParseException {
        final boolean verify = parseJWT.verify(jwsVerifier);

        if (!verify) {
            throw new TokenVerifyException("Token verify fail");
        }

        final JWTClaimsSet jwtClaimsSet = parseJWT.getJWTClaimsSet();

        if (jwtClaimsSet == null) {
            throw new TokenVerifyException("No content found");
        }

        Date expiredTime = jwtClaimsSet.getExpirationTime();

        long now = new Date().getTime();

        if (expiredTime != null && expiredTime.getTime() < now) {
            throw new TokenVerifyException("Token is expired");
        }

        Date notBeforeTime = jwtClaimsSet.getNotBeforeTime();

        if (notBeforeTime != null && now < notBeforeTime.getTime()) {
            throw new TokenVerifyException("Token has not taken effect now");
        }

        String payload = jwtClaimsSet.getStringClaim(JwtUtils.CLAIM_PAYLOAD);

        if (JwtUtils.isEmpty(payload)) {
            throw new TokenVerifyException(JwtUtils.TOKEN_VERIFY_ERROR,
                    new NullPointerException("Payload can not be null"));
        }

        return payload;
    }

    /**
     * 该方法为减少外部库/框架依赖而写，其他地方请不要引用
     *
     * @param src
     * @return
     */
    public static boolean isEmpty(String src) {
        return src == null || "".equals(src.trim());
    }

    /**
     * 获取内置 JwtService 的工厂方法。
     *
     * 优先采用 HMAC 算法实现
     *
     * @param jwtConfig
     * @return
     */
    public static JwtService obtainJwtService(JwtConfig jwtConfig) {
        if (!JwtUtils.isEmpty(jwtConfig.getHmacKey())) {
            return new HMACJwtServiceImpl(jwtConfig);
        }

        return new RSAJwtServiceImpl(jwtConfig);
    }
}
