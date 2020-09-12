package io.ron.jwt.exception;

public class KeyGenerateException extends JwtException  {

    public KeyGenerateException(String message) {
        super(message);
    }

    public KeyGenerateException(String message, Throwable cause) {
        super(message, cause);
    }
}
