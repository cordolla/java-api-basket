package com.basket.api.modules.leagueTeam.records;

import java.time.LocalDateTime;
import java.util.UUID;

public record LeagueTeamResponseDTO(
        UUID associationId,
        UUID leagueId,
        String leagueName,
        UUID teamId,
        String teamName,
        LocalDateTime createdAt
) {}