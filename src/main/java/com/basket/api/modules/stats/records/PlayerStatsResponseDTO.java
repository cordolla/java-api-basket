package com.basket.api.modules.stats.records;

import java.util.UUID;

public record PlayerStatsResponseDTO(
        UUID playerId,
        String playerName,
        String teamName,
        Integer points1,
        Integer points2,
        Integer points3,
        Integer totalPoints,
        Integer fouls,
        Integer assists,
        Integer rebounds
) {}