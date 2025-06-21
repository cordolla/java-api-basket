package com.basket.api.modules.game.repository;

import com.basket.api.modules.game.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<GameEntity, UUID> {
}
