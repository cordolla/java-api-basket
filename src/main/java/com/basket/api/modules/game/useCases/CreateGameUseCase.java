package com.basket.api.modules.game.useCases;

import com.basket.api.modules.game.records.GameRequestDTO;
import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.entity.GameStatus;
import com.basket.api.modules.game.repository.GameRepository;
import com.basket.api.modules.league.entity.LeagueEntity;
import com.basket.api.modules.league.repository.LeagueRepository;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.repository.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateGameUseCase {

    private final GameRepository gameRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;

    public CreateGameUseCase(GameRepository gameRepository, LeagueRepository leagueRepository, TeamRepository teamRepository) {
        this.gameRepository = gameRepository;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
    }

    public GameEntity execute(GameRequestDTO gameRequestDTO) {
        LeagueEntity league = leagueRepository.findById(gameRequestDTO.getLeagueId())
                .orElseThrow(() -> new RuntimeException("League not found"));

        TeamEntity homeTeam = teamRepository.findById(gameRequestDTO.getHomeTeamId())
                .orElseThrow(() -> new RuntimeException("Home team not found"));

        TeamEntity awayTeam = teamRepository.findById(gameRequestDTO.getAwayTeamId())
                .orElseThrow(() -> new RuntimeException("Away team not found"));

        if (homeTeam.equals(awayTeam)) {
            throw new RuntimeException("Home team and away team cannot be the same");
        }

        GameEntity game = new GameEntity();
        game.setLeague(league);
        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);
        game.setVenue(gameRequestDTO.getVenue());
        game.setScheduledDate(gameRequestDTO.getScheduledDate());
        game.setStatus(gameRequestDTO.getStatus() != null ? gameRequestDTO.getStatus() : GameStatus.SCHEDULED);

        return gameRepository.save(game);
    }
}
