package com.basket.api.modules.team.useCases;

import com.basket.api.modules.category.dto.CategoryResponseDTO;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.records.TeamResponseDTO;
import com.basket.api.modules.team.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListTeamUseCase {


    private final TeamRepository teamRepository;

    public ListTeamUseCase(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamResponseDTO execute(UUID id) {
        TeamEntity team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not exists"));

        List<CategoryResponseDTO> categoryDTOs = team.getCategoryEntityList()
                .stream()
                .map(category -> new CategoryResponseDTO(
                        category.getCategory().getId(),
                        category.getCategory().getName(),
                        category.getCategory().getDescription(),
                        category.getCategory().getCategoryGender()
                ))
                .toList();

        return new TeamResponseDTO(
                team.getId(),
                team.getName(),
                team.getShortName(),
                team.getLogoUrl(),
                team.getLocation(),
                team.getDescription(),
                team.getRanking(),
                categoryDTOs
        );
    }
}
