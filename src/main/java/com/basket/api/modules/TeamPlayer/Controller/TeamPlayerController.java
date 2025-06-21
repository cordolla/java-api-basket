package com.basket.api.modules.TeamPlayer.Controller;

import com.basket.api.modules.TeamPlayer.dto.ListPlayersDTO;
import com.basket.api.modules.TeamPlayer.dto.TeamPlayerRequestDTO;
import com.basket.api.modules.TeamPlayer.entity.TeamPlayerEntity;
import com.basket.api.modules.TeamPlayer.useCases.AddTeamPlayerUseCase;
import com.basket.api.modules.TeamPlayer.useCases.ListPlayerByTeamUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/team")
public class TeamPlayerController {

    @Autowired
    private AddTeamPlayerUseCase addTeamPlayerUseCase;

    @Autowired
    private ListPlayerByTeamUseCase listPlayerByTeamUseCase;

    @PostMapping("/{teamId}/add/player/{playerId}")
    public ResponseEntity<Object> addPlayerToTeam(@PathVariable UUID teamId, @PathVariable UUID playerId) {
        try {
            TeamPlayerRequestDTO requestDTO = new TeamPlayerRequestDTO(playerId, teamId);
            var result = addTeamPlayerUseCase.execute(requestDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{teamId}/list/player")
    public ResponseEntity<List<ListPlayersDTO>> listPlayerByTeam(@PathVariable UUID teamId) {
        try {
            var result = listPlayerByTeamUseCase.execute(teamId);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

