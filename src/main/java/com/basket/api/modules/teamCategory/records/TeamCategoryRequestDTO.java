package com.basket.api.modules.teamCategory.records;

import java.util.UUID;

public record TeamCategoryRequestDTO(UUID teamId, UUID categoryId) {
}
