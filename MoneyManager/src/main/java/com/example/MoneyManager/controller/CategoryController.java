package com.example.MoneyManager.controller;

import com.example.MoneyManager.dto.CategoryDTO;
import com.example.MoneyManager.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO newCategory = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        List<CategoryDTO> categories = categoryService.getCategoriesForCurrentUser();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByType(@PathVariable String type){
        List<CategoryDTO> categoris = categoryService.getCategoriesByTypeForCurrentUser(type);
        return ResponseEntity.ok(categoris);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategories(@PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO){
        CategoryDTO updatedCategory = categoryService.updateCategoriesForCurrentUser(categoryId, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }
}
