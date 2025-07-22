package com.basket.api.modules.game.entity;

import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.league.entity.LeagueEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    private LeagueEntity league;

    @ManyToOne
    @JoinColumn(name = "home_team_id", nullable = false)
    private TeamEntity homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id", nullable = false)
    private TeamEntity awayTeam;

    @Column(length = 100)
    private String venue;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDateTime scheduledDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private GameStatus status = GameStatus.SCHEDULED;

    @Column(name = "home_score", nullable = false)
    private Integer homeScore = 0;

    @Column(name = "away_score", nullable = false)
    private Integer awayScore = 0;

    @Column(name = "current_period", nullable = false)
    private Integer currentPeriod = 0;

    @Column(name = "period_time", nullable = false)
    private Integer periodTime = 0;

    @Column(name = "is_overtime", nullable = false)
    private Boolean isOvertime = false;

    @Lob
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}