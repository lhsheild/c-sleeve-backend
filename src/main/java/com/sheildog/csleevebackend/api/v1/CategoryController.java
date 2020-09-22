package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.model.Category;
import com.sheildog.csleevebackend.service.CategoryService;
import com.sheildog.csleevebackend.vo.CategoriesAllVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public CategoriesAllVO getALL(){
        Map<Integer, List<Category>> categories = categoryService.getAll();
        return categories;
    }
}
