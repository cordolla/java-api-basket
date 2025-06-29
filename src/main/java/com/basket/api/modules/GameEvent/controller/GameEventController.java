package com.basket.api.modules.GameEvent.controller;

import com.basket.api.modules.GameEvent.dto.GameEventRequestDTO;
import com.basket.api.modules.GameEvent.entity.GameEventEntity;
import com.basket.api.modules.GameEvent.useCases.AddGameEventUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games/events")
public class GameEventController {

    @Autowired
    private AddGameEventUseCase addGameEventUseCase;

    @PostMapping
    public ResponseEntity<GameEventEntity> createGameEvent(@RequestBody GameEventRequestDTO request) {
        try {
            GameEventEntity event = addGameEventUseCase.execute(request);
            return ResponseEntity.ok(event);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
