package com.basket.api.modules.game.records;

import com.basket.api.modules.game.entity.GameStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameRequestDTO(
        UUID leagueId,
        UUID homeTeamId,
        UUID awayTeamId,
        String venue,
        LocalDateTime scheduledDate,
        GameStatus status
) {
}
