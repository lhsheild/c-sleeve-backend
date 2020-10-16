package com.sheildog.csleevebackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Where(clause = "delete_time is null")
public class Coupon extends BaseEntity {
    @Id
    private Long id;
    private String title;
    @Column(name = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private Long activityId;
    private String remark;
    private Boolean wholeStore;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "couponList")
    private List<Category> categoryList;
}
