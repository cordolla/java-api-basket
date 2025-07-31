package com.basket.api.modules.league.records;

import java.util.UUID;

public record TopScorerDTO(
        int rank,
        UUID playerId,
        String playerName,
        String teamName,
        String teamLogoUrl,
        int gamesPlayed,
        int totalPoints,
        int totalFouls,
        int totalAssists,
        int totalRebounds,
        double pointsPerGame
) {}