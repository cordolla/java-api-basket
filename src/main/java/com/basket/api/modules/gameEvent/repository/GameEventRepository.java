package com.basket.api.modules.gameEvent.repository;

import com.basket.api.modules.gameEvent.entity.GameEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameEventRepository extends JpaRepository<GameEventEntity, UUID> {
    List<GameEventEntity> findByGameId(UUID gameId);
}