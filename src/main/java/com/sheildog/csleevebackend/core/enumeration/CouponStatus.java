package com.sheildog.csleevebackend.core.enumeration;

import java.util.stream.Stream;

public enum CouponStatus {
    AVAILABLE(1, "可以使用"),
    USED(2, "已使用"),
    EXPIRED(3, "已过期");

    private Integer value;
    private String desciption;

    public Integer getValue() {
        return this.value;
    }

    public static CouponStatus toType(int value) {
        return Stream.of(CouponStatus.values())
                .filter(c -> c.value == value)
                .findAny()
                .orElse(null);
    }

    CouponStatus(Integer value, String description) {
        this.value = value;
        this.desciption = desciption;
    }
}
