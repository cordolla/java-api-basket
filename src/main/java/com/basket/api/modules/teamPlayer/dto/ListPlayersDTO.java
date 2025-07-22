package com.basket.api.modules.teamPlayer.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListPlayersDTO {
    UUID id;
    String firstName;
    String lastName;
    String document;
    String nickName;
    Date birthDate;
    Float height;
    Integer jerseyNumber;
    String photoURL;
}
