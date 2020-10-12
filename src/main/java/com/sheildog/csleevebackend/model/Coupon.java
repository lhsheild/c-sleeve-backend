package com.sheildog.csleevebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Coupon extends BaseEntity {
    @Id
    private Long id;
    private String title;
    private Timestamp startTime;
    private Timestamp endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private short type;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp deleteTime;
    private Object activityId;
    private String remark;
    private Object wholeStore;
}
