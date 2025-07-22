package com.basket.api.modules.leagueTeam.useCases;

import com.basket.api.modules.leagueTeam.records.ListTeamDTO;
import com.basket.api.modules.leagueTeam.entity.LeagueTeamEntity;
import com.basket.api.modules.leagueTeam.repository.LeagueTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListLeagueTeamsUseCase {


    private final LeagueTeamRepository leagueTeamRepository;

    public ListLeagueTeamsUseCase(LeagueTeamRepository leagueTeamRepository) {
        this.leagueTeamRepository = leagueTeamRepository;
    }

    public List<ListTeamDTO> execute(UUID leagueId) {
        List<LeagueTeamEntity> listLeagueTeams = this.leagueTeamRepository.findByLeagueId(leagueId);

        return listLeagueTeams.stream()
                .map(leagueTeam -> new ListTeamDTO(
                                leagueTeam.getTeam().getId(),
                                leagueTeam.getTeam().getName(),
                                leagueTeam.getTeam().getLogoUrl(),
                                leagueTeam.getTeam().getLocation()
                        )
                ).toList();
    }
}
