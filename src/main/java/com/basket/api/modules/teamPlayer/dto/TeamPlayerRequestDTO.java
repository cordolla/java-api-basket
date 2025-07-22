package com.basket.api.modules.teamPlayer.dto;

import java.util.UUID;

public record TeamPlayerRequestDTO(UUID playerId, UUID teamId) {
}
