package com.basket.api.modules.team.useCases;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListTeamUseCase {


    private final TeamRepository teamRepository;

    public ListTeamUseCase(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamEntity execute(UUID id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team com o ID " + id + "não encontrado."));
    }
}
