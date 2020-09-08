package com.sheildog.csleevebackend.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "theme_spu", schema = "sleeve", catalog = "")
public class ThemeSpu {
    @Id
    private Long id;
    private Long themeId;
    private Long spuId;
}
