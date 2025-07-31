package com.basket.api.modules.stats.repository;


import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.stats.entity.PlayerGameStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerGameStatsRepository extends JpaRepository<PlayerGameStatsEntity, UUID> {
    List<PlayerGameStatsEntity> findByGameId(UUID gameId);

    Optional<PlayerGameStatsEntity> findByGameIdAndPlayerId(UUID gameId, UUID playerId);

    List<PlayerGameStatsEntity> findByGameIn(List<GameEntity> games);
}