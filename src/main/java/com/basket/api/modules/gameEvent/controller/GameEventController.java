package com.basket.api.modules.gameEvent.controller;

import com.basket.api.modules.gameEvent.records.GameEventRequestDTO;
import com.basket.api.modules.gameEvent.entity.GameEventEntity;
import com.basket.api.modules.gameEvent.useCases.AddGameEventUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games/events")
public class GameEventController {

    private final AddGameEventUseCase addGameEventUseCase;

    public GameEventController(AddGameEventUseCase addGameEventUseCase) {
        this.addGameEventUseCase = addGameEventUseCase;
    }

    @PostMapping
    public ResponseEntity<GameEventEntity> createGameEvent(@RequestBody GameEventRequestDTO request) {
        GameEventEntity event = addGameEventUseCase.execute(request);
        return ResponseEntity.ok(event);
    }
}
