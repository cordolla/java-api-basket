package com.basket.api.modules.LeagueTeam.controller;

import com.basket.api.modules.LeagueTeam.dto.ListTeamDTO;
import com.basket.api.modules.LeagueTeam.useCases.AddTeamToLeagueUseCase;
import com.basket.api.modules.LeagueTeam.useCases.ListLeagueTeamsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/league")
public class LeagueTeamController {

    @Autowired
    private AddTeamToLeagueUseCase addTeamToLeagueUseCase;

    @Autowired
    private ListLeagueTeamsUseCase listLeagueTeamsUseCase;

    @PostMapping("/{leagueId}/team/{teamId}")
    public ResponseEntity<Object> AddTeamToLeague(@PathVariable UUID leagueId, @PathVariable UUID teamId) {
        try {
            var result = this.addTeamToLeagueUseCase.execute(leagueId, teamId);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/teams")
    public ResponseEntity<List<ListTeamDTO>> getTeam(@PathVariable UUID id) {
        try {
            List<ListTeamDTO> result = listLeagueTeamsUseCase.execute(id);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
