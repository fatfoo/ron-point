package io.ron.demo.validator.use.enums;

public enum Gender {

    UNKNOWN,

    MALE,

    FEMALE;

    /**
     * 判断取值是否有效
     *
     * @param val
     * @return
     */
    public static boolean isValid(Integer val) {
        return Gender.values().length > val;
    }
}
