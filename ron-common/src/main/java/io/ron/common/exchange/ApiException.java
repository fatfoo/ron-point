package io.ron.common.exchange;

/**
 * 将其他异常转化为该通用异常，通过全局异常处理机制来捕获并处理
 */
public class ApiException extends RuntimeException {

    private BaseResultCode code = CommonResultCode.SERVER_ERROR;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(BaseResultCode code, String message) {
        super(message);
        this.code = code;
    }

    public BaseResultCode getCode() {
        return code;
    }
}
