package com.basket.api.modules.game.useCases;

import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListGameByLeagueIdUseCase {

    private final GameRepository gameRepository;

    public ListGameByLeagueIdUseCase(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameEntity> execute(UUID leagueId) {
        return gameRepository.findByLeagueId(leagueId);
    }
}
