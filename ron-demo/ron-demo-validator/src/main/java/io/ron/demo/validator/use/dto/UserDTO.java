package io.ron.demo.validator.use.dto;

import io.ron.demo.validator.use.enums.Gender;
import io.ron.demo.validator.use.validator.annotation.EnumValue;
import io.ron.demo.validator.use.validator.group.Update;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

public class UserDTO {

    @NotNull(message = "用户ID不能为空", groups = { Update.class })
    private Long id;

    @NotBlank(message = "用户账号不能为空", groups = { Update.class, Default.class })
    @Size(min = 6, max = 11, message = "账号长度必须是6-11个字符")
    private String name;

    @Size(min = 6, max = 16, message = "密码长度必须是6-16个字符")
    private String password;

    @Range(min = 0, max = 2, message = "性别只能为 0：未知，1：男，2：女")
    @EnumValue(enumClass = Gender.class)
    private int sex;

    @NotNull(message = "地址信息不能为空")
    @Valid
    private AddressDTO address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", address=" + address +
                '}';
    }
}
