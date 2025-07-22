package com.basket.api.modules.category.dto;

import com.basket.api.modules.category.entity.CategoryGender;

import java.util.UUID;

public record CategoryResponseDTO(UUID id, String name, String description, CategoryGender categoryGender) {
}
