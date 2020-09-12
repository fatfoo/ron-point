package io.ron.jwt;

public class JwtConfig {

    // JWT 在 HTTP HEADER 中默认的 KEY
    private String tokenName = JwtUtils.DEFAULT_TOKEN_NAME;

    // HMAC 密钥，用于支持 HMAC 算法
    private String hmacKey;

    // JKS 密钥路径，用于支持 RSA 算法
    private String jksFileName;

    // JKS 密钥密码，用于支持 RSA 算法
    private String jksPassword;

    // 证书密码，用于支持 RSA 算法
    private String certPassword;

    // JWT 标准信息：签发人 - iss
    private String issuer;

    // JWT 标准信息：主题 - sub
    private String subject;

    // JWT 标准信息：受众 - aud
    private String audience;

    // JWT 标准信息：生效时间 - nbf，未来多长时间内生效
    private long notBeforeIn;

    // JWT 标准信息：生效时间 - nbf，具体哪个时间生效
    private long notBeforeAt;

    // JWT 标准信息：过期时间 - exp，未来多长时间内过期
    private long expiredIn;

    // JWT 标准信息：过期时间 - exp，具体哪个时间过期
    private long expiredAt;

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getHmacKey() {
        return hmacKey;
    }

    public void setHmacKey(String hmacKey) {
        this.hmacKey = hmacKey;
    }

    public String getJksFileName() {
        return jksFileName;
    }

    public void setJksFileName(String jksFileName) {
        this.jksFileName = jksFileName;
    }

    public String getJksPassword() {
        return jksPassword;
    }

    public void setJksPassword(String jksPassword) {
        this.jksPassword = jksPassword;
    }

    public String getCertPassword() {
        return certPassword;
    }

    public void setCertPassword(String certPassword) {
        this.certPassword = certPassword;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public long getNotBeforeIn() {
        return notBeforeIn;
    }

    public void setNotBeforeIn(long notBeforeIn) {
        this.notBeforeIn = notBeforeIn;
    }

    public long getNotBeforeAt() {
        return notBeforeAt;
    }

    public void setNotBeforeAt(long notBeforeAt) {
        this.notBeforeAt = notBeforeAt;
    }

    public long getExpiredIn() {
        return expiredIn;
    }

    public void setExpiredIn(long expiredIn) {
        this.expiredIn = expiredIn;
    }

    public long getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(long expiredAt) {
        this.expiredAt = expiredAt;
    }

    @Override
    public String toString() {
        return "JwtConfig{" +
                "tokenName='" + tokenName + '\'' +
                ", hmacKey='" + hmacKey + '\'' +
                ", jksFileName='" + jksFileName + '\'' +
                ", jksPassword='" + jksPassword + '\'' +
                ", certPassword='" + certPassword + '\'' +
                ", issuer='" + issuer + '\'' +
                ", subject='" + subject + '\'' +
                ", audience='" + audience + '\'' +
                ", notBeforeIn=" + notBeforeIn +
                ", notBeforeAt=" + notBeforeAt +
                ", expiredIn=" + expiredIn +
                ", expiredAt=" + expiredAt +
                '}';
    }
}
