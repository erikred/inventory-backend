package com.company.inventory.controller;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
    private final ICategoryService categoryService;
    @Autowired
    public CategoryRestController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Get all the categories.
     * @return ResponseEntity<CategoryResponseRest>
     */
    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseRest> searchCategories() {
        return categoryService.search();
    }

    /**
     * Get category by id.
     * @param id: Id de la category a buscar
     * @return ResponseEntity<CategoryResponseRest>
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> searchCategoriesById(@PathVariable Long id) {
        return categoryService.searchById(id);
    }

    /**
     * Save a new category
     * @param category: Category a guardar.
     * @return ResponseEntity<CategoryResponseRest>
     */
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category){
        return categoryService.save(category);
    }
}
