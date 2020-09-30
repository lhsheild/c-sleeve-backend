package com.sheildog.csleevebackend.dto;

import com.sheildog.csleevebackend.dto.validators.PasswordEqual;
import lombok.*;

//@Builder
@Data
@PasswordEqual(min = 8, max = 32, message = "两次密码不相等")
public class PersonDTO {
    private String name;
    private Integer age;

    private String password1;
    private String password2;
}
