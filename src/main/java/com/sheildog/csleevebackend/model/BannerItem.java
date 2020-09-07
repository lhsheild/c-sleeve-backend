package com.sheildog.csleevebackend.model;

import javax.persistence.*;

@Entity
public class BannerItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String img;
    private String keyword;
    private Short type;

    private Long bannerId;

    @ManyToOne
    @JoinColumn(name = "bannerId", insertable = false, updatable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Banner banner;
}
