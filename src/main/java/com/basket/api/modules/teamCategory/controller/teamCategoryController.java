package com.basket.api.modules.teamCategory.controller;

import com.basket.api.modules.teamCategory.records.ListCategoryDTO;
import com.basket.api.modules.teamCategory.records.TeamCategoryRequestDTO;
import com.basket.api.modules.teamCategory.useCases.AddTeamToCategoryUseCase;
import com.basket.api.modules.teamCategory.useCases.ListTeamCategoryUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/team")
public class teamCategoryController {

    private final AddTeamToCategoryUseCase addTeamToCategoryUseCase;
    private final ListTeamCategoryUseCase listTeamCategoryUseCase;

    public teamCategoryController(AddTeamToCategoryUseCase addTeamToCategoryUseCase, ListTeamCategoryUseCase listTeamCategoryUseCase) {
        this.addTeamToCategoryUseCase = addTeamToCategoryUseCase;
        this.listTeamCategoryUseCase = listTeamCategoryUseCase;
    }

    @PostMapping("{teamId}/categoryId/{categoryId}")
    public ResponseEntity<Object> AddTeamToCategory(@PathVariable UUID teamId, @PathVariable UUID categoryId) {
        TeamCategoryRequestDTO requestDTO = new TeamCategoryRequestDTO(teamId, categoryId);
        var result = this.addTeamToCategoryUseCase.execute(requestDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("{teamId}/categories")
    public ResponseEntity<List<ListCategoryDTO>> getTeam(@PathVariable UUID teamId) {
        List<ListCategoryDTO> result = listTeamCategoryUseCase.execute(teamId);
        return ResponseEntity.ok().body(result);
    }
}
