package com.sheildog.csleevebackend.core.enumeration;

public enum LoginType {
    USER_WX(0, "微信登陆"),
    USER_EMAIL(1, "邮箱登录");

    private Integer value;
    private String description;

    private LoginType(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}
