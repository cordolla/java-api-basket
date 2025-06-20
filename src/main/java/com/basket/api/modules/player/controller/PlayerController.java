package com.basket.api.modules.player.controller;

import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.player.useCases.CreatePlayerUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private CreatePlayerUseCase createPlayerUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> Create(@Valid @RequestBody PlayerEntity playerEntity) {
        try {
            var result = createPlayerUseCase.execute(playerEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
