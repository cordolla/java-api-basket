package com.basket.api.modules.Team.controller;

import com.basket.api.modules.Team.entity.TeamEntity;
import com.basket.api.modules.Team.useCases.CreateTeamUseCase;
import com.basket.api.modules.Team.useCases.ListTeamUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private CreateTeamUseCase createTeamUseCase;

    @Autowired
    private ListTeamUseCase listTeamUseCase;

    @PostMapping
    public ResponseEntity<Object> createTeam(@Valid @RequestBody TeamEntity teamEntity) {
        try {
            var result = this.createTeamUseCase.execute(teamEntity);
            return ResponseEntity.ok().body(result);
        }catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamEntity> getTeam(@PathVariable UUID id) {
        try{
            var result =  this.listTeamUseCase.execute(id);
            return ResponseEntity.ok().body(result);
        }catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
