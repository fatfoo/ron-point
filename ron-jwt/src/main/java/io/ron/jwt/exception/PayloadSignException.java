package io.ron.jwt.exception;

public class PayloadSignException extends JwtException  {

    public PayloadSignException(String message) {
        super(message);
    }

    public PayloadSignException(String message, Throwable cause) {
        super(message, cause);
    }
}
