package com.basket.api.modules.game.records;

import com.basket.api.modules.game.entity.GameStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record GameResponseDTO(
        UUID id,
        GameLeagueResponseDTO league,
        GameTeamResponseDTO homeTeam,
        GameTeamResponseDTO awayTeam,
        String venue,
        LocalDateTime scheduledDate,
        GameStatus status,
        Integer homeScore,
        Integer awayScore
) {}