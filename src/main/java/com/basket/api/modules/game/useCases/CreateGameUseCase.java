package com.basket.api.modules.game.useCases;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.modules.category.entity.CategoryEntity;
import com.basket.api.modules.game.records.GameLeagueResponseDTO;
import com.basket.api.modules.game.records.GameRequestDTO;
import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.entity.GameStatus;
import com.basket.api.modules.game.records.GameResponseDTO;
import com.basket.api.modules.game.records.GameTeamResponseDTO;
import com.basket.api.modules.game.repository.GameRepository;
import com.basket.api.modules.league.entity.LeagueEntity;
import com.basket.api.modules.league.repository.LeagueRepository;
import com.basket.api.modules.leagueTeam.repository.LeagueTeamRepository;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.repository.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateGameUseCase {

    private final GameRepository gameRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final LeagueTeamRepository leagueTeamRepository;

    public CreateGameUseCase(GameRepository gameRepository, LeagueRepository leagueRepository, TeamRepository teamRepository, LeagueTeamRepository leagueTeamRepository) {
        this.gameRepository = gameRepository;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
        this.leagueTeamRepository = leagueTeamRepository;
    }

    public GameResponseDTO execute(GameRequestDTO gameRequestDTO) {
        LeagueEntity league = leagueRepository.findById(gameRequestDTO.leagueId())
                .orElseThrow(() -> new ResourceNotFoundException("League not found"));

        TeamEntity homeTeam = teamRepository.findById(gameRequestDTO.homeTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Home team not found"));

        TeamEntity awayTeam = teamRepository.findById(gameRequestDTO.awayTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Away team not found"));

        if (homeTeam.equals(awayTeam)) {
            throw new BusinessRuleException("Home team and away team cannot be the same");
        }

        CategoryEntity leagueCategory = league.getCategory();

        boolean homeTeamInCategory = homeTeam.getCategoryEntityList().stream()
                .anyMatch(teamCategory -> teamCategory.getCategory().getId().equals(leagueCategory.getId()));

        if (!homeTeamInCategory) {
            throw new BusinessRuleException("O time da casa '" + homeTeam.getName() + "' não pertence à categoria da liga ('" + leagueCategory.getName() + "').");
        }

        boolean awayTeamInCategory = awayTeam.getCategoryEntityList().stream()
                .anyMatch(teamCategory -> teamCategory.getCategory().getId().equals(leagueCategory.getId()));

        if (!awayTeamInCategory) {
            throw new BusinessRuleException("O time visitante '" + awayTeam.getName() + "' não pertence à categoria da liga ('" + leagueCategory.getName() + "').");

        }

        if (!leagueTeamRepository.existsByLeagueAndTeam(league, homeTeam)) {
            throw new BusinessRuleException("O time da casa '" + homeTeam.getName() + "' não está inscrito nesta liga.");
        }

        if (!leagueTeamRepository.existsByLeagueAndTeam(league, awayTeam)) {
            throw new BusinessRuleException("O time visitante '" + awayTeam.getName() + "' não está inscrito nesta liga.");
        }

        GameEntity game = new GameEntity();
        game.setLeague(league);
        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);
        game.setVenue(gameRequestDTO.venue());
        game.setScheduledDate(gameRequestDTO.scheduledDate());
        game.setStatus(gameRequestDTO.status() != null ? gameRequestDTO.status() : GameStatus.SCHEDULED);

        GameEntity savedGame = gameRepository.save(game);

        return new GameResponseDTO(
                savedGame.getId(),
                new GameLeagueResponseDTO(savedGame.getLeague().getId(), savedGame.getLeague().getName()),
                new GameTeamResponseDTO(savedGame.getHomeTeam().getId(), savedGame.getHomeTeam().getName(), savedGame.getHomeTeam().getLogoUrl()),
                new GameTeamResponseDTO(savedGame.getAwayTeam().getId(), savedGame.getAwayTeam().getName(), savedGame.getAwayTeam().getLogoUrl()),
                savedGame.getVenue(),
                savedGame.getScheduledDate(),
                savedGame.getStatus(),
                savedGame.getHomeScore(),
                savedGame.getAwayScore()
        );
    }
}
