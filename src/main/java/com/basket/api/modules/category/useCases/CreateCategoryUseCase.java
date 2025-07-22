package com.basket.api.modules.category.useCases;

import com.basket.api.modules.category.dto.CategoryRequestDTO;
import com.basket.api.modules.category.entity.CategoryEntity;
import com.basket.api.modules.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryUseCase {

    public final CategoryRepository categoryRepository;

    public CreateCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity execute(CategoryRequestDTO categoryRequestDTO) {
        CategoryEntity categoryEntity = categoryRepository.findByName(categoryRequestDTO.name())
                .orElseThrow(() -> new RuntimeException("category already exists"));

        CategoryEntity category = new CategoryEntity();
        category.setName(categoryRequestDTO.name());
        category.setCategoryGender(categoryRequestDTO.categoryGender());

        return categoryRepository.save(category);
    }
}
