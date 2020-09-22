package com.sheildog.csleevebackend.vo;

import com.sheildog.csleevebackend.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CategoriesAllVO {
    private List<Category> roots;
    private List<Category> subs;

    public CategoriesAllVO(Map<Integer, List<Category>> map) {

    }
}
