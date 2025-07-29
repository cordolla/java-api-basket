package com.basket.api.modules.game.controller;

import com.basket.api.modules.game.records.GameRequestDTO;
import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.useCases.CreateGameUseCase;
import com.basket.api.modules.game.useCases.ListGameByLeagueIdUseCase;
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
@RequestMapping("/games")
@Tag(name = "Jogos", description = "Endpoints para agendamento e finalização de jogos")
@SecurityRequirement(name = "bearer-key")
public class GameController {


    private final CreateGameUseCase createGameUseCase;
    private final ListGameByLeagueIdUseCase listGameByLeagueIdUseCase;

    public GameController(CreateGameUseCase createGameUseCase, ListGameByLeagueIdUseCase listGameByLeagueIdUseCase) {
        this.createGameUseCase = createGameUseCase;
        this.listGameByLeagueIdUseCase = listGameByLeagueIdUseCase;
    }

    @PostMapping
    @Operation(summary = "Agenda um novo jogo", description = "Cria um novo jogo com o status 'SCHEDULED'. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jogo agendado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: time da casa igual ao visitante)"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Liga ou time não encontrado")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<GameEntity> createGame(@RequestBody GameRequestDTO gameRequestDTO) {
        GameEntity game = createGameUseCase.execute(gameRequestDTO);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/league/{id}")
    @Operation(summary = "Lista todos os jogos de uma liga", description = "Retorna uma lista de jogos para um ID de liga específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca bem-sucedida"),
            @ApiResponse(responseCode = "404", description = "Liga não encontrada")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<List<GameEntity>> listGamesByLeague(@PathVariable UUID id) {
        List<GameEntity> list = listGameByLeagueIdUseCase.execute(id);
        return ResponseEntity.ok(list);
    }
}
