package com.basket.api.modules.Team.useCases;

import com.basket.api.modules.Team.entity.TeamEntity;
import com.basket.api.modules.Team.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListTeamUseCase {

    @Autowired
    private TeamRepository teamRepository;

    public TeamEntity execute(UUID id) {
        teamRepository.findById(id).orElseThrow(() -> new Error("Team not found"));

        return teamRepository.findById(id).orElseThrow(() -> new Error("Team not found"));
    }
}
