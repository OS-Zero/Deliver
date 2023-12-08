package com.oszero.deliver.admin.validator;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.admin.annotation.JsonString;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * JSON 校验
 *
 * @author oszero
 * @version 1.0.0
 */
public class JsonStringValidator implements ConstraintValidator<JsonString, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            // 尝试使用 Hutool 的 JSONUtil 解析字符串
            JSONUtil.parseObj(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

