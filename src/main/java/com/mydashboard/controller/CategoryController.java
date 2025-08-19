package com.mydashboard.controller;

import com.mydashboard.entity.Category;
import com.mydashboard.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAllCategories();
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
