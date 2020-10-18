package com.sheildog.csleevebackend.core.enumeration;

import java.util.stream.Stream;

public enum CouponType {
    FULL_MINUS(1, "满减券"),
    FULL_OFF(2, "满减折扣券"),
    NO_THRESHOLD_MINUS(3, "无门槛满减券");

    private int value;
    private String description;

    private CouponType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int value() {
        return this.value;
    }

    public static CouponType toType(int value) {
        return Stream.of(CouponType.values())
                .filter(c -> c.value == value)
                .findAny()
                .orElse(null);
    }
}
