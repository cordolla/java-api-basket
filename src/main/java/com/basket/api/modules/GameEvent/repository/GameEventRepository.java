package com.basket.api.modules.GameEvent.repository;

import com.basket.api.modules.GameEvent.entity.GameEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface GameEventRepository extends JpaRepository<GameEventEntity, UUID> {
    List<GameEventEntity> findByGameId(UUID gameId);
}