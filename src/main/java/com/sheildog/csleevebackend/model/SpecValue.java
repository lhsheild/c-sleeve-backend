package com.sheildog.csleevebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "spec_value", schema = "sleeve", catalog = "")
public class SpecValue extends BaseEntity {
    @Id
    private Long id;
    private String value;
    private Long specId;
    private String extend;
}
