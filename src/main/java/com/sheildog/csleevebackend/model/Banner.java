package com.sheildog.csleevebackend.model;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.List;

/**
 * @author a7818
 */
@Entity
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String img;
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "banner")
//    @ForeignKey(name = "null")
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "banner_id")
    private List<BannerItem> items;
}
