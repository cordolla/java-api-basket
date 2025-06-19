package com.basket.api.modules.Team.controller;

import com.basket.api.modules.Team.entity.TeamEntity;
import com.basket.api.modules.Team.useCases.CreateTeamUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private CreateTeamUseCase createTeamUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> createTeam(@Valid @RequestBody TeamEntity teamEntity) {
        try {
            var result = this.createTeamUseCase.execute(teamEntity);
            return ResponseEntity.ok().body(result);
        }catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
