package com.basket.api.modules.teamPlayer.useCases;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.repository.TeamRepository;
import com.basket.api.modules.teamPlayer.records.TeamPlayerRequestDTO;
import com.basket.api.modules.teamPlayer.entity.TeamPlayerEntity;
import com.basket.api.modules.teamPlayer.repository.TeamPlayerRepository;
import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.player.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AddTeamPlayerUseCase {

    private final TeamPlayerRepository teamPlayerRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public AddTeamPlayerUseCase(TeamPlayerRepository teamPlayerRepository, TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamPlayerRepository = teamPlayerRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public TeamPlayerEntity execute(TeamPlayerRequestDTO teamPlayerRequestDTO) {
        TeamEntity team = teamRepository.findById(teamPlayerRequestDTO.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        PlayerEntity player = playerRepository.findById(teamPlayerRequestDTO.playerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));

        Optional<TeamPlayerEntity> existingAssociation = teamPlayerRepository.findByTeamAndPlayerAndIsActive(team, player, true);
        if (existingAssociation.isPresent()) {
            throw new BusinessRuleException("Player is already in this team");
        }

        teamPlayerRepository.desactivatePreviousAssociations(player);

        TeamPlayerEntity newAssociation = new TeamPlayerEntity();
        newAssociation.setTeam(team);
        newAssociation.setPlayer(player);
        newAssociation.setIsActive(true);
        newAssociation.setStartDate(LocalDateTime.now());

        return teamPlayerRepository.save(newAssociation);
    }
}
