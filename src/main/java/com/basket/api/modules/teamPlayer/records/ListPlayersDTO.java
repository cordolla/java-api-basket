package com.basket.api.modules.teamPlayer.records;

import java.util.Date;
import java.util.UUID;

public record ListPlayersDTO(
        UUID id,
        String firstName,
        String lastName,
        String document,
        String nickName,
        Date birthDate,
        Float height,
        Integer jerseyNumber,
        String photoURL
) {
}
