package com.basket.api.modules.league.useCases;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.modules.league.entity.LeagueEntity;
import com.basket.api.modules.league.repository.LeagueRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetLeagueByIdUseCase {


    private final LeagueRepository leagueRepository;

    public GetLeagueByIdUseCase(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public LeagueEntity execute(UUID id) {
        return leagueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("League not found with ID: " + id));
    }
}
