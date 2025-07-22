package com.basket.api.modules.category.dto;

import com.basket.api.modules.category.entity.CategoryGender;

public record CategoryRequestDTO(String name, CategoryGender categoryGender) {
}
