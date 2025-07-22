package com.basket.api.modules.teamCategory.records;

import com.basket.api.modules.category.entity.CategoryGender;

import java.util.UUID;

public record ListCategoryDTO(
        UUID id,
        String name,
        CategoryGender categoryGender

) {
}
