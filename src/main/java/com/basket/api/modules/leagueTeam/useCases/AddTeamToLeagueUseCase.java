package com.basket.api.modules.leagueTeam.useCases;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.modules.leagueTeam.entity.LeagueTeamEntity;
import com.basket.api.modules.leagueTeam.entity.TeamStatus;
import com.basket.api.modules.leagueTeam.records.LeagueTeamResponseDTO;
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

    public LeagueTeamResponseDTO execute(UUID leagueId, UUID teamId) {
        LeagueEntity league = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new ResourceNotFoundException("League não existe"));

        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Time não existe"));

        boolean exists = leagueTeamRepository.existsByLeagueAndTeam(league, team);
        if (exists) {
            throw new BusinessRuleException("Team already added to this league");
        }

        LeagueTeamEntity leagueTeam = new LeagueTeamEntity();
        leagueTeam.setTeamStatus(TeamStatus.ACTIVE);
        leagueTeam.setLeague(league);
        leagueTeam.setTeam(team);

        LeagueTeamEntity savedAssociation = leagueTeamRepository.save(leagueTeam);

        return new LeagueTeamResponseDTO(
                savedAssociation.getId(),
                savedAssociation.getLeague().getId(),
                savedAssociation.getLeague().getName(),
                savedAssociation.getTeam().getId(),
                savedAssociation.getTeam().getName(),
                savedAssociation.getCreatedAt()
        );
    }
}
