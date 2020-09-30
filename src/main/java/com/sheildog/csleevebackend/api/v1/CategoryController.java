package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.model.Category;
import com.sheildog.csleevebackend.model.GridCategory;
import com.sheildog.csleevebackend.service.CategoryService;
import com.sheildog.csleevebackend.service.GridCategoryService;
import com.sheildog.csleevebackend.vo.CategoriesAllVO;
import org.omg.CosNaming.NamingContextPackage.NotFound;
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

    @Autowired
    private GridCategoryService gridCategoryService;

    @GetMapping("/all")
    public CategoriesAllVO getALL() {
        Map<Integer, List<Category>> categories = categoryService.getAll();
        if (categories.isEmpty()) {
            throw new NotFoundException(30009);
        }
        return new CategoriesAllVO(categories);
    }

    @GetMapping("/grid/all")
    public List<GridCategory> getGridCategoryList() {
        List<GridCategory> gridCategoryList = gridCategoryService.getGridCategoryList();
        if (gridCategoryList.isEmpty()) {
            throw new NotFoundException(30009);
        }
        return gridCategoryList;
    }
}
