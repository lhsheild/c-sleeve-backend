package com.sheildog.csleevebackend.dto.validators;

import com.sheildog.csleevebackend.dto.PersonDTO;
import com.sheildog.csleevebackend.dto.TokenGetDTO;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TokenPasswordValidator implements ConstraintValidator<TokenPassword, String> {
    private int min;
    private int max;

    @Override
    public void initialize(TokenPassword constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)){
            return true;
        }
        return s.length() >= this.min && s.length() <= this.max;
    }
}
