package com.basket.api.modules.Team.useCases;


import com.basket.api.modules.Team.entity.TeamEntity;
import com.basket.api.modules.Team.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateTeamUseCase {

    @Autowired
    private TeamRepository teamRepository;

    public TeamEntity execute(TeamEntity teamEntity) {
        teamRepository.findByName(teamEntity.getName()).ifPresent(team -> {
            throw new Error("Team name already exists");
        });

        return teamRepository.save(teamEntity);
    }
}
