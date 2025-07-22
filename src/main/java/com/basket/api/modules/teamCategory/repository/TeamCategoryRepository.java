package com.basket.api.modules.teamCategory.repository;

import com.basket.api.modules.category.entity.CategoryEntity;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.teamCategory.entity.TeamCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TeamCategoryRepository extends JpaRepository<TeamCategoryEntity, UUID> {
    boolean existsByTeamAndCategory(TeamEntity team, CategoryEntity category);
    List<TeamCategoryEntity> findByTeamId(UUID teamId);

}
