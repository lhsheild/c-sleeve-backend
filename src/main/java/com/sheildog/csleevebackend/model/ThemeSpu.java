package com.sheildog.csleevebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "theme_spu", schema = "sleeve", catalog = "")
public class ThemeSpu {
    @Id
    private Long id;
    private Long themeId;
    private Long spuId;
}
