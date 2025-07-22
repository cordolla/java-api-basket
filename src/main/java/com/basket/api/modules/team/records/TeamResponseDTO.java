package com.basket.api.modules.team.records;

import com.basket.api.modules.category.dto.CategoryResponseDTO;

import java.util.List;
import java.util.UUID;

public record TeamResponseDTO(
        UUID id,
        String name,
        String shortName,
        String logoUrl,
        String location,
        String description,
        Integer ranking,
        List<CategoryResponseDTO> categories
) {
}
