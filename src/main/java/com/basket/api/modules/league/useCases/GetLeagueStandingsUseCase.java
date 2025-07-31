package com.basket.api.modules.league.useCases;

import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.entity.GameStatus;
import com.basket.api.modules.game.repository.GameRepository;
import com.basket.api.modules.league.entity.LeagueEntity;
import com.basket.api.modules.league.records.TeamStandingsDTO;
import com.basket.api.modules.league.repository.LeagueRepository;
import com.basket.api.modules.leagueTeam.entity.LeagueTeamEntity;
import com.basket.api.modules.leagueTeam.repository.LeagueTeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GetLeagueStandingsUseCase {

    private final LeagueRepository leagueRepository;
    private final LeagueTeamRepository leagueTeamRepository;
    private final GameRepository gameRepository;

    public GetLeagueStandingsUseCase(LeagueRepository leagueRepository, LeagueTeamRepository leagueTeamRepository, GameRepository gameRepository) {
        this.leagueRepository = leagueRepository;
        this.leagueTeamRepository = leagueTeamRepository;
        this.gameRepository = gameRepository;
    }

    public List<TeamStandingsDTO> execute(UUID leagueId) {
        LeagueEntity league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new ResourceNotFoundException("Liga não encontrada com o ID: " + leagueId));

        List<LeagueTeamEntity> leagueTeams = leagueTeamRepository.findByLeagueId(league.getId());

        List<GameEntity> completedGames = gameRepository.findByLeagueIdAndStatus(league.getId(), GameStatus.COMPLETED);

        List<TeamStandingsDTO> standings = new ArrayList<>();

        for (LeagueTeamEntity leagueTeam : leagueTeams) {
            UUID teamId = leagueTeam.getTeam().getId();
            int wins = 0;
            int losses = 0;
            int pointsFor = 0;
            int pointsAgainst = 0;

            for (GameEntity game : completedGames) {
                if (game.getHomeTeam().getId().equals(teamId)) {
                    pointsFor += game.getHomeScore();
                    pointsAgainst += game.getAwayScore();
                    if (game.getHomeScore() > game.getAwayScore()) {
                        wins++;
                    } else {
                        losses++;
                    }
                } else if (game.getAwayTeam().getId().equals(teamId)) {
                    pointsFor += game.getAwayScore();
                    pointsAgainst += game.getHomeScore();
                    if (game.getAwayScore() > game.getHomeScore()) {
                        wins++;
                    } else {
                        losses++;
                    }
                }
            }

            int gamesPlayed = wins + losses;
            int classificationPoints = (wins * 2) + (losses * 1);
            int pointsDifference = pointsFor - pointsAgainst;
            double winningPercentage = (gamesPlayed == 0) ? 0.0 : ((double) classificationPoints / (gamesPlayed * 2)) * 100;

            standings.add(new TeamStandingsDTO(
                    teamId,
                    leagueTeam.getTeam().getName(),
                    leagueTeam.getTeam().getLogoUrl(),
                    0,
                    gamesPlayed,
                    wins,
                    losses,
                    classificationPoints,
                    pointsFor,
                    pointsAgainst,
                    pointsDifference,
                    winningPercentage
            ));
        }

        standings.sort(Comparator.comparing(TeamStandingsDTO::points).reversed()
                .thenComparing(Comparator.comparing(TeamStandingsDTO::pointsDifference).reversed()));

        return IntStream.range(0, standings.size())
                .mapToObj(i -> {
                    TeamStandingsDTO s = standings.get(i);
                    return new TeamStandingsDTO(s.teamId(), s.teamName(), s.teamLogoUrl(), i + 1, s.gamesPlayed(), s.wins(), s.losses(), s.points(), s.pointsFor(), s.pointsAgainst(), s.pointsDifference(), s.winningPercentage());
                })
                .collect(Collectors.toList());
    }
}