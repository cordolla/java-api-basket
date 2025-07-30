package com.basket.api.modules.teamPlayer.useCases;

import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.teamPlayer.entity.TeamPlayerEntity;
import com.basket.api.modules.teamPlayer.records.ListPlayersDTO;
import com.basket.api.modules.teamPlayer.repository.TeamPlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListPlayersByTeamAndCategoryUseCase {

    private final TeamPlayerRepository teamPlayerRepository;

    public ListPlayersByTeamAndCategoryUseCase(TeamPlayerRepository teamPlayerRepository) {
        this.teamPlayerRepository = teamPlayerRepository;
    }

    public List<ListPlayersDTO> execute(UUID teamId, UUID categoryId){
        List<TeamPlayerEntity> teamPlayers = teamPlayerRepository.findByTeamIdAndCategoryIdAndIsActive(teamId, categoryId, true);

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
                            player.getPhotoURL(),
                            teamPlayer.getCategory().getName()
                    );
                })
                .collect(Collectors.toList());

    }
}
