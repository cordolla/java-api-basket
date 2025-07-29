package com.basket.api.modules.teamPlayer.repository;

import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.teamPlayer.entity.TeamPlayerEntity;
import com.basket.api.modules.player.entity.PlayerEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeamPlayerRepository extends JpaRepository<TeamPlayerEntity, UUID> {
    Optional<TeamPlayerEntity> findByTeamAndPlayerAndIsActive(TeamEntity team, PlayerEntity player, Boolean isActive);
    List<TeamPlayerEntity> findByTeamIdAndIsActive(UUID teamId,  Boolean isActive);

    List<TeamPlayerEntity> findByPlayerAndIsActive(PlayerEntity player, Boolean isActive);

    @Modifying
    @Transactional
    @Query("UPDATE team_player tp SET tp.isActive = false, tp.endDate = CURRENT_TIMESTAMP WHERE tp.player = :player AND tp.isActive = true")
    void desactivatePreviousAssociations(@Param("player") PlayerEntity player);
}
