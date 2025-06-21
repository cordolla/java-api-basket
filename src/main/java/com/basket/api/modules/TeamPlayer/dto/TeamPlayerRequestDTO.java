package com.basket.api.modules.TeamPlayer.dto;

import java.util.UUID;

public record TeamPlayerRequestDTO(UUID playerId, UUID teamId) {
}
