package com.sheildog.csleevebackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Spec {
    @JsonProperty("key_id")
    private Long keyId;
    private String key;
    @JsonProperty("value_id")
    private Long valueId;
    private String value;
}
