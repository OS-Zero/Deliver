package com.oszero.deliver.admin.annotation;

import com.oszero.deliver.admin.validator.JsonStringValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 对参数的JSON类型判断
 *
 * @author oszero
 * @version 1.0.0
 */
@Documented
@Constraint(validatedBy = JsonStringValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonString {
    String message() default "必须为 JSON 类型字符串";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

