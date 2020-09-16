package com.sheildog.csleevebackend.model;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.sheildog.csleevebackend.util.ListAndJson;
import com.sheildog.csleevebackend.util.MapAndJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
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
//    @Convert(converter = MapAndJson.class)
//    private Map<String, Object> test;
//    @Convert(converter = ListAndJson.class)
//    private List<Object> specs;
    private String specs;
    private String code;
    private Long stock;
}
