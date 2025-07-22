package com.basket.api.modules.team.controller;

import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.records.TeamResponseDTO;
import com.basket.api.modules.team.useCases.CreateTeamUseCase;
import com.basket.api.modules.team.useCases.ListTeamUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/teams")
public class TeamController {


    private final CreateTeamUseCase createTeamUseCase;
    private final ListTeamUseCase listTeamUseCase;

    public TeamController(CreateTeamUseCase createTeamUseCase, ListTeamUseCase listTeamUseCase) {
        this.createTeamUseCase = createTeamUseCase;
        this.listTeamUseCase = listTeamUseCase;
    }

    @PostMapping
    public ResponseEntity<Object> createTeam(@Valid @RequestBody TeamEntity teamEntity) {
        var result = this.createTeamUseCase.execute(teamEntity);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> getTeam(@PathVariable UUID id) {
        var result = this.listTeamUseCase.execute(id);
        return ResponseEntity.ok().body(result);
    }
}
