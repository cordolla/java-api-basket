package com.basket.api.modules.game.records;

import java.util.UUID;

public record GameTeamResponseDTO(UUID id, String name, String logoUrl) {}