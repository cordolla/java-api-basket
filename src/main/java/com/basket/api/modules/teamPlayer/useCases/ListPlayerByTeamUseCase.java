package com.basket.api.modules.teamPlayer.useCases;

import com.basket.api.modules.teamPlayer.records.ListPlayersDTO;
import com.basket.api.modules.teamPlayer.entity.TeamPlayerEntity;
import com.basket.api.modules.teamPlayer.repository.TeamPlayerRepository;
import com.basket.api.modules.player.entity.PlayerEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListPlayerByTeamUseCase {

    private final TeamPlayerRepository teamPlayerRepository;

    public ListPlayerByTeamUseCase(TeamPlayerRepository teamPlayerRepository) {
        this.teamPlayerRepository = teamPlayerRepository;
    }

    public List<ListPlayersDTO> execute(UUID teamId) {
        List<TeamPlayerEntity> teamPlayers = teamPlayerRepository.findByTeamIdAndIsActive(teamId, true);

        return teamPlayers.stream()
                .map(teamPlayer -> {
                    PlayerEntity player = teamPlayer.getPlayer();
                    return new ListPlayersDTO(
                            player.getId(),
                            player.getFirstName(),
                            player.getLastName(),
                            player.getDocument(),
                            player.getNickName(),
                            player.getBirthDate(),
                            player.getHeight(),
                            player.getJerseyNumber(),
                            player.getPhotoURL()
                    );
                })
                .toList();
    }
}
