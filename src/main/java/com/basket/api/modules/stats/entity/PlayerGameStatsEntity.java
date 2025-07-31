package com.basket.api.modules.stats.entity;

import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.team.entity.TeamEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity(name = "player_game_stats")
public class PlayerGameStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private GameEntity game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;

    private Integer points1 = 0;
    private Integer points2 = 0;
    private Integer points3 = 0;
    private Integer fouls = 0;
    private Integer assists = 0;
    private Integer rebounds = 0;
}