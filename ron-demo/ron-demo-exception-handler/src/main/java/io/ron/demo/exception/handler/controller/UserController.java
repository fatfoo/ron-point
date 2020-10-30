package io.ron.demo.exception.handler.controller;

import io.ron.demo.exception.handler.dto.UserDTO;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
// 项目中有两个 UserController 类，如果不显式指定其中一个的别名，Spring 将无法注入
@RestController("userControllerUseValidator")
@RequestMapping("/validator/use")
public class UserController {

    /**
     * 创建用户，通过 Content-Type: application/x-www-form-urlencoded 提交
     *
     * Validator 校验失败将抛出 {@link BindException}
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/create")
    public UserDTO create(@Validated UserDTO userDTO) {
        // 通过 Validator 校验参数，开始处理用户插入等逻辑，操作成功以后响应
        return userDTO;
    }

    /**
     * 创建用户，通过 Content-Type: application/json 提交
     *
     * Validator 校验失败将抛出 {@link MethodArgumentNotValidException}
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/create-in-request-body")
    public UserDTO createInRequestBody(@Validated @RequestBody UserDTO userDTO) {
        // 通过 Validator 校验参数，开始处理用户插入等逻辑，操作成功以后响应
        return userDTO;
    }

    /**
     * 测试 @PathVariable 参数的校验
     *
     * Validator 校验失败将抛出 {@link ConstraintViolationException}
     *
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public UserDTO retrieve(@PathVariable("id") @Min(value = 10, message = "id 必须大于 10") Long id) {
        return buildUserDTO("lfy", "qwerty", 1);
    }

    /**
     * 测试 @RequestParam 参数校验
     *
     * 在方法上加 @Validated 无法校验 @RequestParam 与 @PathVariable
     *
     * 必须在类上 @Validated
     *
     * Validator 校验失败将抛出 {@link ConstraintViolationException}
     *
     * @param name
     * @param password
     * @param sex
     * @return
     */
    // @Validated
    @GetMapping("/validate")
    public UserDTO validate(@NotNull @Size(min = 6, max = 11, message = "账号长度必须是6-11个字符") @RequestParam("name") String name,
                            @RequestParam("password") @Size(min = 6, max = 16, message = "密码长度必须是6-16个字符") String password,
                            @RequestParam("sex") @Range(min = 0, max = 2, message = "性别只能为 0：未知，1：男，2：女") int sex) {
        return buildUserDTO(name, password, sex);
    }

    private UserDTO buildUserDTO(String name, String password, int sex) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(name);
        userDTO.setPassword(password);
        userDTO.setSex(sex);
        return userDTO;
    }
}
