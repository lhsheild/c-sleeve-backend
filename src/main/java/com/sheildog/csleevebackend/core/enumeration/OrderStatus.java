package com.sheildog.csleevebackend.core.enumeration;

import java.util.stream.Stream;

/**
 * @author a7818
 */

public enum OrderStatus {
    ALL(0, "全部"),
    UNPAID(1, "待支付"),
    PAID(2, "已支付"),
    DELIVERED(3, "已发货"),
    FINISHED(4, "已完成"),
    CANCELED(5, "已取消"),

    PAID_BUT_OUT_OF(21, "已支付,但无货或库存不足"),
    DEAL_OUT_OF(22, "已处理缺货但已支付的情况");

    private Integer value;
    private String desciption;

    OrderStatus(Integer value, String description) {
        this.value = value;
        this.desciption = desciption;
    }

    private OrderStatus(int value, String text) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static OrderStatus toType(int value) {
        return Stream.of(OrderStatus.values())
                .filter(o -> o.value == value)
                .findAny()
                .orElse(null);
    }
}
