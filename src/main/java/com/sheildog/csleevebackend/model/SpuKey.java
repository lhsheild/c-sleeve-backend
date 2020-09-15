package com.sheildog.csleevebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "spu_key", schema = "sleeve")
public class SpuKey {
    @Id
    private Long id;
    private Long spuId;
    private Long specKeyId;
}
