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
@Table(name = "spec_key", schema = "sleeve", catalog = "")
public class SpecKey extends BaseEntity {
    @Id
    private Long id;
    private String name;
    private String unit;
    private Boolean standard;
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "specId")
    private List<SpecValue> specValueList;
}
