package com.basket.api.modules.league.useCases;

import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.entity.GameStatus;
import com.basket.api.modules.game.repository.GameRepository;
import com.basket.api.modules.league.records.ThreePointLeaderDTO;
import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.stats.entity.PlayerGameStatsEntity;
import com.basket.api.modules.stats.repository.PlayerGameStatsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GetThreePointLeadersUseCase {

    private final GameRepository gameRepository;
    private final PlayerGameStatsRepository playerGameStatsRepository;

    public GetThreePointLeadersUseCase(GameRepository gameRepository, PlayerGameStatsRepository playerGameStatsRepository) {
        this.gameRepository = gameRepository;
        this.playerGameStatsRepository = playerGameStatsRepository;
    }

    public Page<ThreePointLeaderDTO> execute(UUID leagueId, PageRequest pageRequest) {
        List<GameEntity> completedGames = gameRepository.findByLeagueIdAndStatus(leagueId, GameStatus.COMPLETED);
        if (completedGames.isEmpty()) {
            return Page.empty(pageRequest);
        }

        List<PlayerGameStatsEntity> allStats = playerGameStatsRepository.findByGameIn(completedGames);

        Map<PlayerEntity, List<PlayerGameStatsEntity>> statsByPlayer = allStats.stream()
                .collect(Collectors.groupingBy(PlayerGameStatsEntity::getPlayer));

        List<ThreePointLeaderDTO> threePointLeaders = new ArrayList<>();

        for (Map.Entry<PlayerEntity, List<PlayerGameStatsEntity>> entry : statsByPlayer.entrySet()) {
            PlayerEntity player = entry.getKey();
            List<PlayerGameStatsEntity> playerStats = entry.getValue();

            int gamesPlayed = playerStats.size();
            int totalThreePointers = playerStats.stream().mapToInt(PlayerGameStatsEntity::getPoints3).sum();
            int pointsFromThreePointers = totalThreePointers * 3;
            double threePointersPerGame = (double) totalThreePointers / gamesPlayed;

            String teamName = playerStats.getLast().getTeam().getName();
            String teamLogoUrl = playerStats.getLast().getTeam().getLogoUrl();

            threePointLeaders.add(new ThreePointLeaderDTO(
                    0, player.getId(), player.getFirstName() + " " + player.getLastName(),
                    teamName, teamLogoUrl, gamesPlayed, totalThreePointers, pointsFromThreePointers, threePointersPerGame
            ));
        }

        threePointLeaders.sort(Comparator.comparing(ThreePointLeaderDTO::totalThreePointers).reversed());

        List<ThreePointLeaderDTO> rankedLeaders = IntStream.range(0, threePointLeaders.size())
                .mapToObj(i -> {
                    ThreePointLeaderDTO s = threePointLeaders.get(i);
                    return new ThreePointLeaderDTO(i + 1, s.playerId(), s.playerName(), s.teamName(), s.teamLogoUrl(), s.gamesPlayed(), s.totalThreePointers(), s.pointsFromThreePointers(), s.threePointersPerGame());
                })
                .collect(Collectors.toList());

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), rankedLeaders.size());

        if (start > rankedLeaders.size()) {
            return new PageImpl<>(new ArrayList<>(), pageRequest, rankedLeaders.size());
        }

        List<ThreePointLeaderDTO> pageContent = rankedLeaders.subList(start, end);

        return new PageImpl<>(pageContent, pageRequest, rankedLeaders.size());
    }
}