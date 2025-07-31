package com.basket.api.modules.league.records;

import java.util.UUID;

public record ThreePointLeaderDTO(
        int rank,
        UUID playerId,
        String playerName,
        String teamName,
        String teamLogoUrl,
        int gamesPlayed,
        int totalThreePointers,
        int pointsFromThreePointers,
        double threePointersPerGame
) {}