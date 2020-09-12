package io.ron.jwt.exception;

public class TokenVerifyException extends JwtException {

    public TokenVerifyException(String message) {
        super(message);
    }

    public TokenVerifyException(String message, Throwable cause) {
        super(message, cause);
    }
}
