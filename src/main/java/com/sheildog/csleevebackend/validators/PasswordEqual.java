package com.sheildog.csleevebackend.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordEqual {
    int min();
    int max();
    String message() default "passwords are not equal";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
