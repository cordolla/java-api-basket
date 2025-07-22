package com.basket.api.modules.teamCategory.records;

import java.util.UUID;

public record TeamCategoryResponseDTO(UUID teamCategoryId, UUID teamId, UUID categoryId, String teamName, String categoryName) {
}