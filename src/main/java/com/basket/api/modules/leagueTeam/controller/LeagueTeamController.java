package com.basket.api.modules.leagueTeam.controller;

import com.basket.api.modules.leagueTeam.dto.ListTeamDTO;
import com.basket.api.modules.leagueTeam.useCases.AddTeamToLeagueUseCase;
import com.basket.api.modules.leagueTeam.useCases.ListLeagueTeamsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/league")
public class LeagueTeamController {


    private final AddTeamToLeagueUseCase addTeamToLeagueUseCase;
    private final ListLeagueTeamsUseCase listLeagueTeamsUseCase;

    public LeagueTeamController(AddTeamToLeagueUseCase addTeamToLeagueUseCase, ListLeagueTeamsUseCase listLeagueTeamsUseCase) {
        this.addTeamToLeagueUseCase = addTeamToLeagueUseCase;
        this.listLeagueTeamsUseCase = listLeagueTeamsUseCase;
    }

    @PostMapping("/{leagueId}/team/{teamId}")
    public ResponseEntity<Object> AddTeamToLeague(@PathVariable UUID leagueId, @PathVariable UUID teamId) {
        var result = this.addTeamToLeagueUseCase.execute(leagueId, teamId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}/teams")
    public ResponseEntity<List<ListTeamDTO>> getTeam(@PathVariable UUID id) {
        List<ListTeamDTO> result = listLeagueTeamsUseCase.execute(id);
        return ResponseEntity.ok().body(result);
    }
}
