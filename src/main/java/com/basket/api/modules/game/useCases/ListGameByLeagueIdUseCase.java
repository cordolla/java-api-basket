package com.basket.api.modules.game.useCases;

import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.repository.GameRepository;
import com.basket.api.modules.league.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListGameByLeagueIdUseCase {

    @Autowired
    private GameRepository gameRepository;

    public List<GameEntity> execute(UUID leagueId){
        return gameRepository.findByLeagueId(leagueId);
    }
}
