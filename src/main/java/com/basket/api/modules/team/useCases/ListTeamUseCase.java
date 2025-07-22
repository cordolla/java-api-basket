package com.basket.api.modules.team.useCases;

import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.repository.TeamRepository;
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
