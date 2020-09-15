package com.sheildog.csleevebackend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Spec {
    private Long keyId;
    private String key;
    private Long valueId;
    private String value;
}