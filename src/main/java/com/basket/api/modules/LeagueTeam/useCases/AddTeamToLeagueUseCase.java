package com.basket.api.modules.LeagueTeam.useCases;

import com.basket.api.modules.LeagueTeam.entity.LeagueTeamEntity;
import com.basket.api.modules.LeagueTeam.entity.TeamStatus;
import com.basket.api.modules.LeagueTeam.repository.LeagueTeamRepository;
import com.basket.api.modules.Team.entity.TeamEntity;
import com.basket.api.modules.Team.repository.TeamRepository;
import com.basket.api.modules.league.entity.LeagueEntity;
import com.basket.api.modules.league.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddTeamToLeagueUseCase {

    @Autowired
    private LeagueTeamRepository leagueTeamRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    public LeagueTeamEntity execute(UUID leagueId, UUID teamId) {
        LeagueEntity league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("League not found"));

        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

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
