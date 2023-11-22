package com.oszero.deliver.admin.annotation;

import com.oszero.deliver.admin.validator.JsonStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = JsonStringValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonString {
    String message() default "必须为 JSON 类型字符串";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

