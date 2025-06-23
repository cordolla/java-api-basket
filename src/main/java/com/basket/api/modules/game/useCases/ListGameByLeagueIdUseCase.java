package com.basket.api.modules.game.useCases;

import com.basket.api.modules.game.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListGameByLeagueIdUseCase {

    @Autowired
    private GameRepository gameRepository;

    public ListGameByLeagueIdUseCase(GameRepository gameRepository) {

    }
}
