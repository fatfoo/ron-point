package io.ron.common.exchange;

/**
 * 通用响应编码
 */
public final class CommonResultCode extends BaseResultCode {

    public static final String CODE_NAME_OK = "OK";

    public static final String CODE_NAME_CREATED_OR_MODIFIED = "CREATED_OR_MODIFIED";

    public static final String CODE_NAME_NO_CONTENT = "NO_CONTENT";

    public static final String CODE_NAME_BAD_REQUEST = "BAD_REQUEST";

    public static final String CODE_NAME_UNAUTHORIZED = "UNAUTHORIZED";

    public static final String CODE_NAME_FORBIDDEN = "FORBIDDEN";

    public static final String CODE_NAME_NOT_FOUND = "NOT_FOUND";

    public static final String CODE_NAME_SERVER_ERROR = "SERVER_ERROR";

    private CommonResultCode(String name, int code, String message) {
        super(name, code, message);
    }

    public static final CommonResultCode OK = new CommonResultCode(CODE_NAME_OK, 200, "ok");

    public static final CommonResultCode CREATED_OR_MODIFIED =
            new CommonResultCode(CODE_NAME_CREATED_OR_MODIFIED, 201, "created or modified");

    public static final CommonResultCode NO_CONTENT =
            new CommonResultCode(CODE_NAME_NO_CONTENT, 204, "no content");

    public static final CommonResultCode BAD_REQUEST =
            new CommonResultCode(CODE_NAME_BAD_REQUEST, 400, "bad request");

    public static final CommonResultCode UNAUTHORIZED =
            new CommonResultCode(CODE_NAME_UNAUTHORIZED, 401, "unauthorized");

    public static final CommonResultCode FORBIDDEN =
            new CommonResultCode(CODE_NAME_FORBIDDEN, 403, "forbidden");

    public static final CommonResultCode NOT_FOUND =
            new CommonResultCode(CODE_NAME_NOT_FOUND,404, "not found");

    public static final CommonResultCode SERVER_ERROR =
            new CommonResultCode(CODE_NAME_SERVER_ERROR, 500, "server error");

}
