package com.basket.api.modules.gameEvent.records;

import com.basket.api.modules.gameEvent.entity.GameEventType;

import java.util.UUID;

public record GameEventRequestDTO(
        UUID gameId,
        UUID teamId,
        UUID playerId,
        GameEventType eventType,
        Integer eventTime,
        Integer points
) {
}
