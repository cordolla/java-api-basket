package com.basket.api.modules.game.controller;

import com.basket.api.modules.game.dto.GameRequestDTO;
import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.useCases.CreateGameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private CreateGameUseCase createGameUseCase;

    @PostMapping
    public ResponseEntity<GameEntity> createGame(@RequestBody GameRequestDTO gameRequestDTO) {
        try {
            GameEntity game = createGameUseCase.execute(gameRequestDTO);
            return ResponseEntity.ok(game);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
