package com.basket.api.modules.leagueTeam.records;

import java.util.UUID;

public record ListTeamDTO(
        UUID id,
        String name,
        String logoUrl,
        String location
) {
}
