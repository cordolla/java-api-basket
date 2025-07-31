package com.basket.api.modules.stats.records;

import com.basket.api.modules.game.entity.GameStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GameStatsResponseDTO(
        UUID gameId,
        String homeTeamName,
        String awayTeamName,
        Integer homeScore,
        Integer awayScore,
        LocalDateTime scheduledDate,
        GameStatus status,
        List<PlayerStatsResponseDTO> playerStats
) {}