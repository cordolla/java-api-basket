package com.basket.api.modules.leagueTeam.useCases;

import com.basket.api.modules.leagueTeam.entity.LeagueTeamEntity;
import com.basket.api.modules.leagueTeam.entity.TeamStatus;
import com.basket.api.modules.leagueTeam.repository.LeagueTeamRepository;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.repository.TeamRepository;
import com.basket.api.modules.league.entity.LeagueEntity;
import com.basket.api.modules.league.repository.LeagueRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddTeamToLeagueUseCase {

    private final LeagueTeamRepository leagueTeamRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;

    public AddTeamToLeagueUseCase(LeagueTeamRepository leagueTeamRepository, TeamRepository teamRepository, LeagueRepository leagueRepository) {
        this.leagueTeamRepository = leagueTeamRepository;
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
    }

    public LeagueTeamEntity execute(UUID leagueId, UUID teamId) {
        LeagueEntity league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League não existe"));

        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Time não existe"));

        boolean exists = leagueTeamRepository.existsByLeagueAndTeam(league, team);
        if (exists) {
            throw new RuntimeException("Team already added to this league");
        }

        // Criar associação
        LeagueTeamEntity leagueTeam = new LeagueTeamEntity();
        leagueTeam.setTeamStatus(TeamStatus.ACTIVE);
        leagueTeam.setLeague(league);
        leagueTeam.setTeam(team);

        return leagueTeamRepository.save(leagueTeam);
    }
}
