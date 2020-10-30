package io.ron.demo.validator.not.controller;

import io.ron.demo.validator.not.dto.AddressDTO;
import io.ron.demo.validator.not.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validator/not")
public class UserController {

    @PostMapping("/create")
    public UserDTO create(@RequestBody UserDTO userDTO) {

        if (userDTO == null) {
            // 此为示例代码，正式项目中一般不使用 System.out.println 打印日志
            System.out.println("用户信息不能为空");
            return null;
        }

        // 校验用户账户
        String name = userDTO.getName();
        if (name == null || name.trim() == "") {
            System.out.println("用户账号不能为空");
            return null;
        } else {
            int len = name.trim().length();
            if (len < 6 || len > 11) {
                System.out.println("密码长度必须是6-11个字符");
                return null;
            }
        }

        // 校验密码，抽出一个方法，与校验用户账户的代码做比较
        if (validatePassword(userDTO) == null) {
            return null;
        }

        // 校验性别
        int sex = userDTO.getSex();
        if (sex < 0 || sex > 2) {
            System.out.println("性别只能为 0：未知，1：男，2：女");
            return null;
        }

        // 校验地址
        validateAddress(userDTO.getAddress());

        // 校验完成后，请求的用户信息有效，开始处理用户插入等逻辑，操作成功以后响应。
        return userDTO;
    }

    // 校验地址，通过抛出异常的方式来处理
    private void validateAddress(AddressDTO addressDTO) {
        if (addressDTO == null) {
            // 也可以通过抛出异常来处理
            throw new RuntimeException("地址信息不能为空");
        }

        validateAddressField(addressDTO.getProvince(), "所在省份不能为空");

        validateAddressField(addressDTO.getCity(), "所在城市不能为空");

        validateAddressField(addressDTO.getDetail(), "详细地址不能为空");
    }

    // 校验地址中的每个字段，并返回对应的信息
    private void validateAddressField(String field, String msg) {
        if (field == null || field.equals("")) {
            throw new RuntimeException(msg);
        }
    }

    // 将校验密码的操作抽取到一个方法中
    private UserDTO validatePassword(@RequestBody UserDTO userDTO) {
        String password = userDTO.getPassword();

        if (password == null || password.trim() == "") {
            System.out.println("用户密码不能为空");
            return null;
        } else {
            int len = password.trim().length();
            if (len < 6 || len > 16) {
                System.out.println("账号长度必须是6-16个字符");
                return null;
            }
        }

        return userDTO;
    }
}
