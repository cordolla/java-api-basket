package com.basket.api.modules.game.records;

import com.basket.api.modules.game.entity.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameRequestDTO {

    private UUID leagueId;
    private UUID homeTeamId;
    private UUID awayTeamId;
    private String venue;
    private LocalDateTime scheduledDate;
    private GameStatus status;
}
