package com.sheildog.csleevebackend.dto;

import com.sheildog.csleevebackend.core.enumeration.LoginType;
import com.sheildog.csleevebackend.dto.validators.TokenPassword;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenGetDTO {
    @NotBlank(message = "Account不能为空")
    private String account;
    @TokenPassword(max=30, message="{token.password}")
//    @TokenPassword(max=30)
    private String password;
    private LoginType loginType;
}
