package io.ron.demo.validator.not.dto;

public class UserDTO {

    /**
     * 校验规则：
     *
     * 1. 用户账号不能为空
     * 2. 账号长度必须是6-11个字符
     */
    private String name;

    /**
     * 校验规则：
     * 1. 密码长度必须是6-16个字符
     */
    private String password;

    /**
     * 校验规则：
     *
     * 1. 性别只能为 0：未知，1：男，2：女"
     */
    private int sex;

    /**
     * 校验规则：
     *
     * 1. 地址信息不能为空
     */
    private AddressDTO address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", address=" + address +
                '}';
    }
}
