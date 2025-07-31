package com.basket.api.modules.league.useCases;

import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.entity.GameStatus;
import com.basket.api.modules.game.repository.GameRepository;
import com.basket.api.modules.league.records.TopScorerDTO;
import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.stats.entity.PlayerGameStatsEntity;
import com.basket.api.modules.stats.repository.PlayerGameStatsRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GetTopScorersUseCase {

    private final GameRepository gameRepository;
    private final PlayerGameStatsRepository playerGameStatsRepository;

    public GetTopScorersUseCase(GameRepository gameRepository, PlayerGameStatsRepository playerGameStatsRepository) {
        this.gameRepository = gameRepository;
        this.playerGameStatsRepository = playerGameStatsRepository;
    }

    public List<TopScorerDTO> execute(UUID leagueId) {
        List<GameEntity> completedGames = gameRepository.findByLeagueIdAndStatus(leagueId, GameStatus.COMPLETED);
        if (completedGames.isEmpty()) {
            return new ArrayList<>();
        }

        List<PlayerGameStatsEntity> allStats = playerGameStatsRepository.findByGameIn(completedGames);

        Map<PlayerEntity, List<PlayerGameStatsEntity>> statsByPlayer = allStats.stream()
                .collect(Collectors.groupingBy(PlayerGameStatsEntity::getPlayer));

        List<TopScorerDTO> topScorers = new ArrayList<>();

        for (Map.Entry<PlayerEntity, List<PlayerGameStatsEntity>> entry : statsByPlayer.entrySet()) {
            PlayerEntity player = entry.getKey();
            List<PlayerGameStatsEntity> playerStats = entry.getValue();

            int gamesPlayed = playerStats.size();
            int totalPoints = playerStats.stream().mapToInt(s -> s.getPoints1() + (s.getPoints2() * 2) + (s.getPoints3() * 3)).sum();
            int totalFouls = playerStats.stream().mapToInt(PlayerGameStatsEntity::getFouls).sum();
            int totalAssists = playerStats.stream().mapToInt(PlayerGameStatsEntity::getAssists).sum();
            int totalRebounds = playerStats.stream().mapToInt(PlayerGameStatsEntity::getRebounds).sum();
            double pointsPerGame = (double) totalPoints / gamesPlayed;

            // Pega o time mais recente do jogador
            String teamName = playerStats.getLast().getTeam().getName();
            String teamLogoUrl = playerStats.getLast().getTeam().getLogoUrl();

            topScorers.add(new TopScorerDTO(
                    0, player.getId(), player.getFirstName() + " " + player.getLastName(),
                    teamName, teamLogoUrl, gamesPlayed, totalPoints, totalFouls, totalAssists,
                    totalRebounds, pointsPerGame
            ));
        }

        topScorers.sort(Comparator.comparing(TopScorerDTO::totalPoints).reversed());

        return IntStream.range(0, topScorers.size())
                .mapToObj(i -> {
                    TopScorerDTO s = topScorers.get(i);
                    return new TopScorerDTO(i + 1, s.playerId(), s.playerName(), s.teamName(), s.teamLogoUrl(), s.gamesPlayed(), s.totalPoints(), s.totalFouls(), s.totalAssists(), s.totalRebounds(), s.pointsPerGame());
                })
                .collect(Collectors.toList());
    }
}