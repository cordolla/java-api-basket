package com.basket.api.modules.game.repository;

import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.entity.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<GameEntity, UUID> {
    List<GameEntity> findByLeagueId(UUID leagueId);

    List<GameEntity> findByLeagueIdAndStatus(UUID leagueId, GameStatus status);
}
