package com.basket.api.modules.Team.repository;

import com.basket.api.modules.Team.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {
    Optional<TeamEntity> findByName(String name);
}
