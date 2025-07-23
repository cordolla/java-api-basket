package com.basket.api.modules.league.repository;

import com.basket.api.modules.league.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LeagueRepository extends JpaRepository<LeagueEntity, UUID> {
    Optional<LeagueEntity> findByName(String name);
}
