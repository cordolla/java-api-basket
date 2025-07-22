package com.basket.api.modules.game.controller;

import com.basket.api.modules.game.dto.GameRequestDTO;
import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.useCases.CreateGameUseCase;
import com.basket.api.modules.game.useCases.ListGameByLeagueIdUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GameController {


    private final CreateGameUseCase createGameUseCase;
    private final ListGameByLeagueIdUseCase listGameByLeagueIdUseCase;

    public GameController(CreateGameUseCase createGameUseCase, ListGameByLeagueIdUseCase listGameByLeagueIdUseCase) {
        this.createGameUseCase = createGameUseCase;
        this.listGameByLeagueIdUseCase = listGameByLeagueIdUseCase;
    }

    @PostMapping
    public ResponseEntity<GameEntity> createGame(@RequestBody GameRequestDTO gameRequestDTO) {
        GameEntity game = createGameUseCase.execute(gameRequestDTO);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/league/{id}")
    public ResponseEntity<List<GameEntity>> listGamesByLeague(@PathVariable UUID id) {
        List<GameEntity> list = listGameByLeagueIdUseCase.execute(id);
        return ResponseEntity.ok(list);
    }
}
