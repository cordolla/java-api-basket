package com.basket.api.modules.teamPlayer.useCases;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.repository.TeamRepository;
import com.basket.api.modules.teamCategory.entity.TeamCategoryEntity;
import com.basket.api.modules.teamCategory.repository.TeamCategoryRepository;
import com.basket.api.modules.teamPlayer.records.TeamPlayerRequestDTO;
import com.basket.api.modules.teamPlayer.entity.TeamPlayerEntity;
import com.basket.api.modules.teamPlayer.repository.TeamPlayerRepository;
import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.player.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AddTeamPlayerUseCase {

    private final TeamPlayerRepository teamPlayerRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final TeamCategoryRepository teamCategoryRepository;

    public AddTeamPlayerUseCase(TeamPlayerRepository teamPlayerRepository, TeamRepository teamRepository, PlayerRepository playerRepository, TeamCategoryRepository teamCategoryRepository) {
        this.teamPlayerRepository = teamPlayerRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.teamCategoryRepository = teamCategoryRepository;
    }

    @Transactional
    public TeamPlayerEntity execute(TeamPlayerRequestDTO teamPlayerRequestDTO) {
        TeamEntity destinationTeam = teamRepository.findById(teamPlayerRequestDTO.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        PlayerEntity player = playerRepository.findById(teamPlayerRequestDTO.playerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));

        Optional<TeamPlayerEntity> existingAssociation = teamPlayerRepository.findByTeamAndPlayerAndIsActive(destinationTeam, player, true);
        if (existingAssociation.isPresent()) {
            throw new BusinessRuleException("Player is already on this team");
        }

        List<TeamCategoryEntity> destinationTeamCategories = teamCategoryRepository.findByTeamId(destinationTeam.getId());
        if (!destinationTeamCategories.isEmpty()) {
            List<TeamPlayerEntity> playerActiveAssociations = teamPlayerRepository.findByPlayerAndIsActive(player, true);

            for (TeamPlayerEntity existingPlayerAssociation : playerActiveAssociations) {
                TeamEntity existingTeam = existingPlayerAssociation.getTeam();
                List<TeamCategoryEntity> existingTeamCategories = teamCategoryRepository.findByTeamId(existingTeam.getId());

                boolean hasConflict = destinationTeamCategories.stream()
                        .anyMatch(destCategory -> existingTeamCategories.stream()
                                .anyMatch(existingCategory -> existingCategory.getCategory().getId().equals(destCategory.getCategory().getId())));

                if (hasConflict) {
                    throw new BusinessRuleException("This player is already active on another team that competes in the same category.");
                }
            }
        }

        TeamPlayerEntity newAssociation = new TeamPlayerEntity();
        newAssociation.setTeam(destinationTeam);
        newAssociation.setPlayer(player);
        newAssociation.setIsActive(true);
        newAssociation.setStartDate(LocalDateTime.now());

        return teamPlayerRepository.save(newAssociation);
    }
}
