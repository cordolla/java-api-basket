package com.basket.api.modules.league.useCases;

import com.basket.api.modules.league.entity.LeagueEntity;
import com.basket.api.modules.league.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetLeagueByIdUseCase {

    @Autowired
    private LeagueRepository leagueRepository;

    public LeagueEntity execute(UUID id) {
        return leagueRepository.findById(id).orElseThrow(() -> new Error("League not found"));
    }
}
