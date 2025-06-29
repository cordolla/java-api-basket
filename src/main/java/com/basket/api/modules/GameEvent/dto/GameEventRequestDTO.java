package com.basket.api.modules.GameEvent.dto;


import com.basket.api.modules.GameEvent.entity.GameEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameEventRequestDTO {
    private UUID gameId;
    private UUID teamId;
    private UUID playerId; // Opcional para eventos como "timeout"
    private GameEventType eventType;
    private Integer eventTime; // Tempo em segundos
    private Integer points; // Para cestas
}
