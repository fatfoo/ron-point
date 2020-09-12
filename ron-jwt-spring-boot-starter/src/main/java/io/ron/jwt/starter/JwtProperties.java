package io.ron.jwt.starter;

import io.ron.jwt.JwtUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ron.jwt")
public class JwtProperties {

    private String tokenName = JwtUtils.DEFAULT_TOKEN_NAME;

    private String hmacKey;

    private String jksFileName;

    private String jksPassword;

    private String certPassword;

    // 签发人
    private String issuer;

    // 主题
    private String subject;

    // 受众
    private String audience;

    private long notBeforeIn;

    private long notBeforeAt;

    private long expiredIn;

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
        return "JwtProperties{" +
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
