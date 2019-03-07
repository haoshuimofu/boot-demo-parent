package com.demo.boot.base.validator;

import com.demo.boot.base.validator.IsMobile;
import com.demo.boot.base.validator.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author hsw
 * @Date 16:55 2018/7/26
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required || (s != null && s.length() > 0 )) {
            return ValidatorUtils.isMobile(s);
        }
        return true;
    }

}