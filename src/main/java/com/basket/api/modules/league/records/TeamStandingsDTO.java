package com.basket.api.modules.league.records;

import java.util.UUID;

public record TeamStandingsDTO(
        UUID teamId,
        String teamName,
        String teamLogoUrl,
        int position,
        int gamesPlayed,
        int wins,
        int losses,
        int points,
        int pointsFor,
        int pointsAgainst,
        int pointsDifference,
        double winningPercentage
) {}