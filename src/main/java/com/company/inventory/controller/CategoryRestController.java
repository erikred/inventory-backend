package com.company.inventory.controller;

import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
    private final ICategoryService categoryService;

    @Autowired
    public CategoryRestController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseRest> searchCategories() {
        return categoryService.search();
    }
}
