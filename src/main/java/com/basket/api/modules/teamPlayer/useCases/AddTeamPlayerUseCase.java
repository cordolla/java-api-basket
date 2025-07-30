package com.basket.api.modules.teamPlayer.useCases;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.modules.category.entity.CategoryEntity;
import com.basket.api.modules.category.repository.CategoryRepository;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.repository.TeamRepository;
import com.basket.api.modules.teamCategory.repository.TeamCategoryRepository;
import com.basket.api.modules.teamPlayer.records.TeamPlayerRequestDTO;
import com.basket.api.modules.teamPlayer.entity.TeamPlayerEntity;
import com.basket.api.modules.teamPlayer.records.TeamPlayerResponseDTO;
import com.basket.api.modules.teamPlayer.repository.TeamPlayerRepository;
import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.player.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.cert.Extension;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AddTeamPlayerUseCase {

    private final TeamPlayerRepository teamPlayerRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final TeamCategoryRepository teamCategoryRepository;
    private final CategoryRepository categoryRepository;

    public AddTeamPlayerUseCase(TeamPlayerRepository teamPlayerRepository, TeamRepository teamRepository, PlayerRepository playerRepository, TeamCategoryRepository teamCategoryRepository, CategoryRepository categoryRepository) {
        this.teamPlayerRepository = teamPlayerRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.teamCategoryRepository = teamCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public TeamPlayerResponseDTO execute(TeamPlayerRequestDTO teamPlayerRequestDTO) {

        TeamEntity destinationTeam = teamRepository.findById(teamPlayerRequestDTO.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        PlayerEntity player = playerRepository.findById(teamPlayerRequestDTO.playerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));

        CategoryEntity category = categoryRepository.findById(teamPlayerRequestDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));


        boolean teamHasCategory = teamCategoryRepository.existsByTeamAndCategory(destinationTeam, category);

        if (!teamHasCategory) {
            throw new BusinessRuleException(
                    "Não é possível adicionar o jogador. O time '" + destinationTeam.getName() +
                            "' não está registrado na categoria '" + category.getName() + "'.");
        }

        Optional<TeamPlayerEntity> existingActiveAssociation = teamPlayerRepository
                .findByPlayerAndCategoryAndIsActive(player, category, true);

        if (existingActiveAssociation.isPresent()) {
            TeamEntity existingTeam = existingActiveAssociation.get().getTeam();
            if (existingTeam.getId().equals(destinationTeam.getId())) {
                throw new BusinessRuleException(
                        "O jogador já está ativo nesta equipe (Time: " + destinationTeam.getName() +
                                ", Categoria: " + category.getName() + ").");
            } else {
                throw new BusinessRuleException(
                        "Este jogador já está ativo na categoria '" + category.getName() +
                                "' pelo time '" + existingTeam.getName() + "'.");
            }
        }

        TeamPlayerEntity newAssociation = new TeamPlayerEntity();
        newAssociation.setTeam(destinationTeam);
        newAssociation.setPlayer(player);
        newAssociation.setCategory(category);
        newAssociation.setIsActive(true);
        newAssociation.setStartDate(LocalDateTime.now());

        TeamPlayerEntity savedAssociation = teamPlayerRepository.save(newAssociation);

        return new TeamPlayerResponseDTO(
                savedAssociation.getId(),
                savedAssociation.getTeam().getId(),
                savedAssociation.getTeam().getName(),
                savedAssociation.getPlayer().getId(),
                savedAssociation.getPlayer().getFirstName() + " " + savedAssociation.getPlayer().getLastName(),
                savedAssociation.getCategory().getId(),
                savedAssociation.getCategory().getName(),
                savedAssociation.getIsActive(),
                savedAssociation.getStartDate()
        );
    }
}
