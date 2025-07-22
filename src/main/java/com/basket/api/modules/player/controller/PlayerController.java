package com.basket.api.modules.player.controller;

import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.player.useCases.CreatePlayerUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {


    private final CreatePlayerUseCase createPlayerUseCase;

    public PlayerController(CreatePlayerUseCase createPlayerUseCase) {
        this.createPlayerUseCase = createPlayerUseCase;
    }

    @PostMapping
    public ResponseEntity<Object> Create(@Valid @RequestBody PlayerEntity playerEntity) {
        var result = createPlayerUseCase.execute(playerEntity);
        return ResponseEntity.ok().body(result);
    }
}
