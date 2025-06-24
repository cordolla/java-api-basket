package com.basket.api.modules.game.controller;

import com.basket.api.modules.game.dto.GameRequestDTO;
import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.useCases.CreateGameUseCase;
import com.basket.api.modules.game.useCases.ListGameByLeagueIdUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private CreateGameUseCase createGameUseCase;

    @Autowired
    private ListGameByLeagueIdUseCase listGameByLeagueIdUseCase;

    @PostMapping
    public ResponseEntity<GameEntity> createGame(@RequestBody GameRequestDTO gameRequestDTO) {
        try {
            GameEntity game = createGameUseCase.execute(gameRequestDTO);
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/league/{id}")
    public ResponseEntity<List<GameEntity>> listGamesByLeague(@PathVariable UUID id) {
        try {
            List<GameEntity> list = listGameByLeagueIdUseCase.execute(id);
            return ResponseEntity.ok(list);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
