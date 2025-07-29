package com.basket.api.modules.player.controller;

import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.player.useCases.CreatePlayerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
@Tag(name = "5. Jogadores", description = "Endpoints para gerenciamento de jogadores")
@SecurityRequirement(name = "bearer-key")
public class PlayerController {


    private final CreatePlayerUseCase createPlayerUseCase;

    public PlayerController(CreatePlayerUseCase createPlayerUseCase) {
        this.createPlayerUseCase = createPlayerUseCase;
    }

    @PostMapping
    @Operation(summary = "Cria um novo jogador", description = "Cria um novo jogador no sistema. Requer autenticação de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jogador criado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "409", description = "Jogador com este documento já existe")
    })
    public ResponseEntity<Object> Create(@Valid @RequestBody PlayerEntity playerEntity) {
        var result = createPlayerUseCase.execute(playerEntity);
        return ResponseEntity.ok().body(result);
    }
}
