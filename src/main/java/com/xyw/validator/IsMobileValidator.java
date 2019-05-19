package com.xyw.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/19
 * Time:16:24
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {
    @Override
    public void initialize(IsMobile isMobile) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
