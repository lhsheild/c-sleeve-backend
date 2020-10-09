package com.sheildog.csleevebackend.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sheildog.csleevebackend.util.GenericAndJson;
import com.sheildog.csleevebackend.util.MapAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String openid;
    private String nickname;
    private Integer unifyUid;
    private String email;
    private String password;
    private String mobile;
//    private String group;
//    private String wxProfile;
//
//    public Object getWxProfile() {
//        if (this.wxProfile == null) {
//            return Collections.emptyList();
//        }
//        return GenericAndJson.jsonToObject(this.wxProfile, new TypeReference<Object>() {
//        });
//    }
//
//    public void setWxProfile(Object wxProfile) {
//        if (wxProfile == null) {
//            return;
//        }
//        this.wxProfile = GenericAndJson.objectToJson(wxProfile);
//    }

    @Convert(converter = MapAndJson.class)
    private Map<String, Objects> wxProfile;
}
