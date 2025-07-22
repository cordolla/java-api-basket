package com.basket.api.modules.teamCategory.useCases;

import com.basket.api.modules.category.entity.CategoryEntity;
import com.basket.api.modules.category.repository.CategoryRepository;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.repository.TeamRepository;
import com.basket.api.modules.teamCategory.entity.TeamCategoryEntity;
import com.basket.api.modules.teamCategory.records.TeamCategoryRequestDTO;
import com.basket.api.modules.teamCategory.records.TeamCategoryResponseDTO;
import com.basket.api.modules.teamCategory.repository.TeamCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AddTeamToCategoryUseCase {

    public final TeamCategoryRepository teamCategoryRepository;
    public final TeamRepository teamRepository;
    public final CategoryRepository categoryRepository;

    public AddTeamToCategoryUseCase(TeamCategoryRepository teamCategoryRepository, TeamRepository teamRepository, CategoryRepository categoryRepository) {
        this.teamCategoryRepository = teamCategoryRepository;
        this.teamRepository = teamRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public TeamCategoryResponseDTO execute(TeamCategoryRequestDTO teamCategoryRequestDTO) {
        TeamEntity team = teamRepository.findById(teamCategoryRequestDTO.teamId())
                .orElseThrow(() -> new RuntimeException("Time não existe"));

        CategoryEntity category = categoryRepository.findById(teamCategoryRequestDTO.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não existe"));

        boolean exists = teamCategoryRepository.existsByTeamAndCategory(team, category);
        if (exists) {
            throw new RuntimeException("Ja existe um time com essa categoria");
        }

        //criar associação
        TeamCategoryEntity teamCategory = new TeamCategoryEntity();
        teamCategory.setCategory(category);
        teamCategory.setTeam(team);

        TeamCategoryEntity savedEntity = teamCategoryRepository.save(teamCategory);

        return new TeamCategoryResponseDTO(
                savedEntity.getId(),
                savedEntity.getTeam().getId(),
                savedEntity.getCategory().getId(),
                savedEntity.getTeam().getName(),
                savedEntity.getCategory().getName()
        );
    }
}
