package com.basket.api.modules.category.controller;

import com.basket.api.modules.category.dto.CategoryRequestDTO;
import com.basket.api.modules.category.entity.CategoryEntity;
import com.basket.api.modules.category.useCases.CreateCategoryUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
    }

    @PostMapping
    private ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        CategoryEntity category = createCategoryUseCase.execute(categoryRequestDTO);
        return ResponseEntity.ok(category);
    }
}
