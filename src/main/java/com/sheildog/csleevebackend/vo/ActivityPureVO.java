package com.sheildog.csleevebackend.vo;

import com.sheildog.csleevebackend.model.Activity;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author a7818
 */
@Getter
@Setter
@NoArgsConstructor
public class ActivityPureVO {
    private Long id;
    private String title;
    private String entranceImg;
    private Boolean online;
    private String remark;
    private String startTime;
    private String endTime;

    public ActivityPureVO(Activity activity){
        BeanUtils.copyProperties(activity, this);
    }

    public ActivityPureVO(Object object){
        BeanUtils.copyProperties(object, this);
    }
}
