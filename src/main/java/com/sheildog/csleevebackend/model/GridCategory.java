package com.sheildog.csleevebackend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Where(clause = "delete_time is null")
@Table(name = "grid_category", schema = "sleeve")
@Getter
@Setter
public class GridCategory extends BaseEntity {
    @Id
    private Long id;
    private String title;
    private String img;
    private String name;
    private Long categoryId;
    private Long rootCategoryId;
}
