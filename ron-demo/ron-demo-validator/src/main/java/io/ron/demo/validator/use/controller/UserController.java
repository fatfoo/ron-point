package io.ron.demo.validator.use.controller;

import io.ron.demo.validator.use.dto.UserDTO;
import io.ron.demo.validator.use.validator.group.OrderedGroup;
import io.ron.demo.validator.use.validator.group.Update;
import org.hibernate.validator.constraints.Range;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ExceptionDepthComparator;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.ControllerAdviceBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.method.annotation.MapMethodProcessor;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;
import org.springframework.web.method.annotation.ModelMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.handler.AbstractHandlerMethodExceptionResolver;
import org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter;
import org.springframework.web.servlet.mvc.method.annotation.AsyncTaskMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.CallableMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.DeferredResultMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.HttpHeadersReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewResolverMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBodyReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.ViewMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.ViewNameMethodReturnValueHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @PutMapping("/update")
    public UserDTO update(@Validated({Update.class}) @RequestBody UserDTO userDTO) {
        // 通过 Validator 校验参数，开始处理用户插入等逻辑，操作成功以后响应
        return userDTO;
    }

    @PostMapping("/ordered")
    public UserDTO ordered(@Validated({OrderedGroup.class}) @RequestBody UserDTO userDTO) {
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

    @PostMapping("/create-in-request-body-with-binding-result")
    public UserDTO createInRequestBodyWithBindingResult(@Validated @RequestBody UserDTO userDTO, BindingResult result) {
        if(result.hasErrors()){
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
        }
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