package com.basket.api.modules.teamCategory.useCases;

import com.basket.api.modules.teamCategory.entity.TeamCategoryEntity;
import com.basket.api.modules.teamCategory.records.ListCategoryDTO;
import com.basket.api.modules.teamCategory.repository.TeamCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListTeamCategoryUseCase {

    private final TeamCategoryRepository teamCategoryRepository;

    public ListTeamCategoryUseCase(TeamCategoryRepository teamCategoryRepository) {
        this.teamCategoryRepository = teamCategoryRepository;
    }

    public List<ListCategoryDTO> execute(UUID teamId) {
        List<TeamCategoryEntity> listTeamCategories = this.teamCategoryRepository.findByTeamId(teamId);

        return listTeamCategories.stream()
                .map(teamCategory -> new ListCategoryDTO(
                        teamCategory.getCategory().getId(),
                        teamCategory.getCategory().getName(),
                        teamCategory.getCategory().getCategoryGender()
                ))
                .toList();
    }
}
