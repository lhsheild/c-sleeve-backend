package com.sheildog.csleevebackend.dto.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Constraint(validatedBy = TokenPasswordValidator.class)
public @interface TokenPassword {
    int min() default 6;
    int max() default 32;
    String message() default "密码不合法";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
