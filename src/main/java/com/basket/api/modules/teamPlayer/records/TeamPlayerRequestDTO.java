package com.basket.api.modules.teamPlayer.records;

import java.util.UUID;

public record TeamPlayerRequestDTO(UUID playerId, UUID teamId) {
}
