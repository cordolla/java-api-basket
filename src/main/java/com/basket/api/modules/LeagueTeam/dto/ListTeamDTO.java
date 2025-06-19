package com.basket.api.modules.LeagueTeam.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListTeamDTO {
    private UUID id;
    private String name;
    private String logoUrl;
    private String location;
}
