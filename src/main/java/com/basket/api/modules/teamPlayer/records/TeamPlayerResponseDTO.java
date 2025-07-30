package com.basket.api.modules.teamPlayer.records;

import java.time.LocalDateTime;
import java.util.UUID;

public record TeamPlayerResponseDTO(
        UUID associationId,
        UUID teamId,
        String teamName,
        UUID playerId,
        String playerName,
        UUID categoryId,
        String categoryName,
        Boolean isActive,
        LocalDateTime startDate
) {}