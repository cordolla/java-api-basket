package com.basket.api.modules.game.useCases;

import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.records.GameLeagueResponseDTO;
import com.basket.api.modules.game.records.GameResponseDTO;
import com.basket.api.modules.game.records.GameTeamResponseDTO;
import com.basket.api.modules.game.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListGameByLeagueIdUseCase {

    private final GameRepository gameRepository;

    public ListGameByLeagueIdUseCase(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameResponseDTO> execute(UUID leagueId) {
        List<GameEntity> games = gameRepository.findByLeagueId(leagueId);

        return games.stream().map(game -> new GameResponseDTO(
                game.getId(),
                new GameLeagueResponseDTO(game.getLeague().getId(), game.getLeague().getName()),
                new GameTeamResponseDTO(game.getHomeTeam().getId(), game.getHomeTeam().getName(), game.getHomeTeam().getLogoUrl()),
                new GameTeamResponseDTO(game.getAwayTeam().getId(), game.getAwayTeam().getName(), game.getAwayTeam().getLogoUrl()),
                game.getVenue(),
                game.getScheduledDate(),
                game.getStatus(),
                game.getHomeScore(),
                game.getAwayScore()
        )).collect(Collectors.toList());
    }
}