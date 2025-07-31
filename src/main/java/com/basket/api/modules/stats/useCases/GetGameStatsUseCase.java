package com.basket.api.modules.stats.useCases;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.repository.GameRepository;
import com.basket.api.modules.stats.entity.PlayerGameStatsEntity;
import com.basket.api.modules.stats.records.GameStatsResponseDTO;
import com.basket.api.modules.stats.records.PlayerStatsResponseDTO;
import com.basket.api.modules.stats.repository.PlayerGameStatsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetGameStatsUseCase {

    private final GameRepository gameRepository;
    private final PlayerGameStatsRepository playerGameStatsRepository;

    public GetGameStatsUseCase(GameRepository gameRepository, PlayerGameStatsRepository playerGameStatsRepository) {
        this.gameRepository = gameRepository;
        this.playerGameStatsRepository = playerGameStatsRepository;
    }

    public GameStatsResponseDTO execute(UUID gameId) {
        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com o ID: " + gameId));

        List<PlayerGameStatsEntity> statsList = playerGameStatsRepository.findByGameId(gameId);

        List<PlayerStatsResponseDTO> playerStatsDTOs = statsList.stream()
                .map(this::mapToPlayerStatsDTO)
                .collect(Collectors.toList());

        return new GameStatsResponseDTO(
                game.getId(),
                game.getHomeTeam().getName(),
                game.getAwayTeam().getName(),
                game.getHomeScore(),
                game.getAwayScore(),
                game.getScheduledDate(),
                game.getStatus(),
                playerStatsDTOs
        );
    }

    private PlayerStatsResponseDTO mapToPlayerStatsDTO(PlayerGameStatsEntity stats) {
        int totalPoints = (stats.getPoints1()) + (stats.getPoints2() * 2) + (stats.getPoints3() * 3);
        return new PlayerStatsResponseDTO(
                stats.getPlayer().getId(),
                stats.getPlayer().getFirstName() + " " + stats.getPlayer().getLastName(),
                stats.getTeam().getName(),
                stats.getPoints1(),
                stats.getPoints2(),
                stats.getPoints3(),
                totalPoints,
                stats.getFouls(),
                stats.getAssists(),
                stats.getRebounds()
        );
    }
}