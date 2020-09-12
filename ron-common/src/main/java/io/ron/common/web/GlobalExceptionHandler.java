package io.ron.common.web;

import io.ron.common.exchange.ApiException;
import io.ron.common.exchange.BaseResultCode;
import io.ron.common.exchange.CommonResultCode;
import io.ron.common.exchange.CustomResultCode;
import io.ron.common.exchange.Result;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * 如有必要，可以有多个 {@link RestControllerAdvice}
 * 或者 {@link ControllerAdvice} 注解的全局异常处理类，
 * 并标注 {@link Order} 来显示标识处理顺序
 */
@ConditionalOnProperty(prefix = "ron.global-exception-handler", value = "enabled", havingValue = "true")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${ron.global-exception-handler.validator.failfast:false}")
    private boolean failfast;

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // failFast 为 true 时，只要出现校验失败的情况，就立即结束校验，不再进行后续的校验。
                .failFast(failfast)
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator());
        return methodValidationPostProcessor;
    }

    /**
     * https://stackoverflow.com/questions/23211076/bindexception-thrown-instead-of-methodargumentnotvalidexception-in-rest-applicat
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(BindException e) {

        List<ObjectError> oes = e.getAllErrors();

        if (oes.size() > 1) {
            return Result.with(CommonResultCode.BAD_REQUEST, oes.stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }

        String message = oes.get(0).getDefaultMessage();

        try {
            Class<?> parameterType = e.getTarget().getClass();
            String fieldName = e.getBindingResult().getFieldError().getField();
            Field field = parameterType.getDeclaredField(fieldName);

            CustomResultCode annotation = field.getAnnotation(CustomResultCode.class);

            if (annotation == null) {
                return Result.with(CommonResultCode.BAD_REQUEST, message);
            }

            String codeName = annotation.value();

            // Class.forName 初始化静态域
            Class clazz = Class.forName(annotation.type().getName());

            Method method = clazz.getMethod("valueOf", String.class);
            Object o = method.invoke(null, codeName);
            if (o != null && o instanceof BaseResultCode) {
                return Result.with((BaseResultCode) o, message);
            }
        } catch (NoSuchFieldException | ClassNotFoundException | NoSuchMethodException |
                IllegalAccessException | InvocationTargetException e1) {
            e1.printStackTrace();
        }
        return Result.with(CommonResultCode.BAD_REQUEST, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

        List<ObjectError> oes = e.getBindingResult().getAllErrors();

        if (oes.size() > 1) {
            return Result.with(CommonResultCode.BAD_REQUEST, oes.stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }

        String message = oes.get(0).getDefaultMessage();

        try {
            Class<?> parameterType = e.getParameter().getParameterType();
            String fieldName = e.getBindingResult().getFieldError().getField();
            Field field = parameterType.getDeclaredField(fieldName);

            CustomResultCode annotation = field.getAnnotation(CustomResultCode.class);

            if (annotation == null) {
                return Result.with(CommonResultCode.BAD_REQUEST, message);
            }

            String codeName = annotation.value();

            // Class.forName 初始化静态域
            Class clazz = Class.forName(annotation.type().getName());
            Method method = clazz.getMethod("valueOf", String.class);
            Object o = method.invoke(null, codeName);
            if (o != null && o instanceof BaseResultCode) {
                return Result.with((BaseResultCode) o, message);
            }
        } catch (NoSuchFieldException | ClassNotFoundException | NoSuchMethodException |
                IllegalAccessException | InvocationTargetException e1) {
            e1.printStackTrace();
        }
        return Result.with(CommonResultCode.BAD_REQUEST, message);
    }

    /**
     * 处理 @RequestParam 与 @PathVariable 校验失败以后的异常
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(ConstraintViolationException e) {

        List<String> messages = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return Result.with(CommonResultCode.BAD_REQUEST, messages);
    }

    @ExceptionHandler(ApiException.class)
    public Result apiExceptionHandler(ApiException e) {
        return Result.error(e);
    }

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        return Result.error(e);
    }

}
