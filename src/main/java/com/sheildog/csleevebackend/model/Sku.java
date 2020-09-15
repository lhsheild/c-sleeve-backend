package com.sheildog.csleevebackend.model;

import com.sheildog.csleevebackend.util.MapAndJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author a7818
 */
@Entity
@Getter
@Setter
public class Sku extends BaseEntity {
    @Id
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private Long spuId;
    private Long categoryId;
    private Long rootCategoryId;
    @Convert(converter = MapAndJson.class)
    private Map<String, Object> test;
//    private String test;
    private String specs;
    private String code;
    private Long stock;
}
