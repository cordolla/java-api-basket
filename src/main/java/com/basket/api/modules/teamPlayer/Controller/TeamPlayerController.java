package com.basket.api.modules.teamPlayer.Controller;

import com.basket.api.modules.teamPlayer.records.ListPlayersDTO;
import com.basket.api.modules.teamPlayer.records.TeamPlayerRequestDTO;
import com.basket.api.modules.teamPlayer.useCases.AddTeamPlayerUseCase;
import com.basket.api.modules.teamPlayer.useCases.ListPlayerByTeamUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teams/{teamId}/players")
@Tag(name = "9. Associações (Time-Jogador)", description = "Endpoints para associar jogadores a times")
@SecurityRequirement(name = "bearer-key")
public class TeamPlayerController {


    private final AddTeamPlayerUseCase addTeamPlayerUseCase;
    private final ListPlayerByTeamUseCase listPlayerByTeamUseCase;

    public TeamPlayerController(AddTeamPlayerUseCase addTeamPlayerUseCase, ListPlayerByTeamUseCase listPlayerByTeamUseCase) {
        this.addTeamPlayerUseCase = addTeamPlayerUseCase;
        this.listPlayerByTeamUseCase = listPlayerByTeamUseCase;
    }

    @PostMapping("/{playerId}")
    @Operation(summary = "Adiciona um jogador a um time", description = "Cria a associação entre um jogador e um time. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação criada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Time ou jogador não encontrado"),
            @ApiResponse(responseCode = "409", description = "Jogador já pertence a este time")
    })
    public ResponseEntity<Object> addPlayerToTeam(@PathVariable UUID teamId, @PathVariable UUID playerId) {
        TeamPlayerRequestDTO requestDTO = new TeamPlayerRequestDTO(playerId, teamId);
        var result = this.addTeamPlayerUseCase.execute(requestDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @Operation(summary = "Lista os jogadores de um time", description = "Retorna uma lista com todos os jogadores ativos associados a um time específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Time não encontrado")
    })
    public ResponseEntity<List<ListPlayersDTO>> listPlayerByTeam(@PathVariable UUID teamId) {
        var result = listPlayerByTeamUseCase.execute(teamId);
        return ResponseEntity.ok().body(result);
    }
}

