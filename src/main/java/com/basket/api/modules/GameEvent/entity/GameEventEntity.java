package com.basket.api.modules.GameEvent.entity;

import com.basket.api.modules.Team.entity.TeamEntity;
import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.player.entity.PlayerEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "game_events")
public class GameEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameEntity game;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private GameEventType eventType;

    @Column(name = "event_time", nullable = false)
    private Integer eventTime; // Tempo decorrido no jogo, em segundos.

    @Column(name = "points", nullable = true)
    private Integer points; // Número de pontos (para cestas).

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
