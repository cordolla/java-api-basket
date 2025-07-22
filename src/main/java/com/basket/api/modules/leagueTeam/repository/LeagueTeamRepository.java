package com.basket.api.modules.leagueTeam.repository;

import com.basket.api.modules.leagueTeam.entity.LeagueTeamEntity;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.league.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LeagueTeamRepository extends JpaRepository<LeagueTeamEntity, UUID> {
    boolean existsByLeagueAndTeam(LeagueEntity league, TeamEntity team);
    List<LeagueTeamEntity> findByLeagueId(UUID leagueId);
}
