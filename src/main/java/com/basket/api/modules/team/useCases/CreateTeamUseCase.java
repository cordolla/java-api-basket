package com.basket.api.modules.team.useCases;


import com.basket.api.exception.TeamAlreadyExistsException;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.repository.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateTeamUseCase {


    private final TeamRepository teamRepository;

    public CreateTeamUseCase(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamEntity execute(TeamEntity teamEntity) {
        teamRepository.findByName(teamEntity.getName()).ifPresent(team -> {
            throw new TeamAlreadyExistsException("Um time com o nome '" + teamEntity.getName() + "' já existe.");
        });

        return teamRepository.save(teamEntity);
    }
}
