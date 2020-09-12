package io.ron.common.exchange;

public class Result<T> {

    private int code;

    private String msg;

    private T data;

    private Result() {}

    public static <T> Result ok(T data) {
        return Result.with(CommonResultCode.OK, data);
    }

    public static <T> Result with(BaseResultCode code, T data) {
        Result result = new Result();
        result.code = code.getCode();
        result.msg = code.getMessage();
        result.data = data;
        return result;
    }

    public static Result error(Exception e) {
        return Result.with(CommonResultCode.SERVER_ERROR, e.getMessage());
    }

    public static Result error(ApiException e) {
        return Result.with(e.getCode(), e.getMessage());
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
