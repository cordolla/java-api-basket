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
        if (categoryRepository.findByName(categoryRequestDTO.name()).isPresent()){
            throw new RuntimeException("Category already exists");
        }

        CategoryEntity category = new CategoryEntity();
        category.setName(categoryRequestDTO.name());
        category.setDescription(categoryRequestDTO.description());
        category.setCategoryGender(categoryRequestDTO.categoryGender());

        return categoryRepository.save(category);
    }
}
