package com.basket.api.modules.teamPlayer.Controller;

import com.basket.api.modules.teamPlayer.dto.ListPlayersDTO;
import com.basket.api.modules.teamPlayer.dto.TeamPlayerRequestDTO;
import com.basket.api.modules.teamPlayer.useCases.AddTeamPlayerUseCase;
import com.basket.api.modules.teamPlayer.useCases.ListPlayerByTeamUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/team")
public class TeamPlayerController {


    private final AddTeamPlayerUseCase addTeamPlayerUseCase;
    private final ListPlayerByTeamUseCase listPlayerByTeamUseCase;

    public TeamPlayerController(AddTeamPlayerUseCase addTeamPlayerUseCase, ListPlayerByTeamUseCase listPlayerByTeamUseCase) {
        this.addTeamPlayerUseCase = addTeamPlayerUseCase;
        this.listPlayerByTeamUseCase = listPlayerByTeamUseCase;
    }

    @PostMapping("/{teamId}/add/player/{playerId}")
    public ResponseEntity<Object> addPlayerToTeam(@PathVariable UUID teamId, @PathVariable UUID playerId) {
        TeamPlayerRequestDTO requestDTO = new TeamPlayerRequestDTO(playerId, teamId);
        var result = addTeamPlayerUseCase.execute(requestDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{teamId}/list/player")
    public ResponseEntity<List<ListPlayersDTO>> listPlayerByTeam(@PathVariable UUID teamId) {
        var result = listPlayerByTeamUseCase.execute(teamId);
        return ResponseEntity.ok().body(result);
    }
}

