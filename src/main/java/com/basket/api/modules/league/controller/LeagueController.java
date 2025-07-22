package com.basket.api.modules.league.controller;


import com.basket.api.modules.league.entity.LeagueEntity;
import com.basket.api.modules.league.useCases.CreateLeagueUseCase;
import com.basket.api.modules.league.useCases.GetLeagueByIdUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/leagues")
public class LeagueController {


    private final CreateLeagueUseCase createLeagueUseCase;
    private final GetLeagueByIdUseCase getLeagueByIdUseCase;

    public LeagueController(CreateLeagueUseCase createLeagueUseCase, GetLeagueByIdUseCase getLeagueByIdUseCase) {
        this.createLeagueUseCase = createLeagueUseCase;
        this.getLeagueByIdUseCase = getLeagueByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody LeagueEntity league) {
        var result = this.createLeagueUseCase.execute(league);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable UUID id) {
        LeagueEntity league = getLeagueByIdUseCase.execute(id);
        return ResponseEntity.ok(league);
    }

}
