package com.basket.api.modules.stats.records;

import java.util.UUID;

public record PlayerStatsRequestDTO(
        UUID playerId,
        UUID teamId,
        Integer points1,
        Integer points2,
        Integer points3,
        Integer fouls,
        Integer assists,
        Integer rebounds
) {}