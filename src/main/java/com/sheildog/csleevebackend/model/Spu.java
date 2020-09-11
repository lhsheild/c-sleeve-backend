package com.sheildog.csleevebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Spu extends BaseEntity{
    @Id
    private Long id;
    private String title;
    private String subtitle;
    private Long categoryId;
    private Long rootCategoryId;
    private Boolean online;
    private String price;
    private String sketchSpecId;
    private Long defaultSkuId;
    private String img;
    private String discountPrice;
    private String description;
    private String tags;
    private Boolean isTest;
    private String forThemeImg;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "spuId")
    private List<SpuImg> spuImgList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "spuId")
    private List<SpuDetailImg> spuDetailImgList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "spuId")
    private List<Sku> skuList;
}
