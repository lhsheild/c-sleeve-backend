package com.sheildog.csleevebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "sku_spec", schema = "sleeve", catalog = "")
public class SkuSpec {
    @Id
    private Long id;
    private Long spuId;
    private Long skuId;
    private Long keyId;
    private Long valueId;
}
