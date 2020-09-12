package io.ron.common.exchange;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 响应编码基础类
 *
 * 用户自定义编码必须继承该类
 *
 * 具体编码可参考 {@link CommonResultCode}
 */
public class BaseResultCode {

    private String name;

    private int code;

    private String message;

    private static Map<String, BaseResultCode> map = new ConcurrentHashMap<>();

    protected BaseResultCode(String name, int code, String message) {
        this.name = name;
        this.code = code;
        this.message = message;

        map.put(name, this);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static BaseResultCode valueOf(String name) {
        BaseResultCode ac = map.get(name);
        if (ac == null) {
            throw new IllegalArgumentException("No enum constant exists, name is " + name);
        }
        return ac;
    }

    public static Collection<? extends BaseResultCode> values() {
        return map.values();
    }
}
