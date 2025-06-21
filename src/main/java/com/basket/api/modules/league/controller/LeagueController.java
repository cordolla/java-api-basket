package com.basket.api.modules.league.controller;


import com.basket.api.modules.league.entity.LeagueEntity;
import com.basket.api.modules.league.useCases.CreateLeagueUseCase;
import com.basket.api.modules.league.useCases.GetLeagueByIdUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/leagues")
public class LeagueController {

    @Autowired
    private CreateLeagueUseCase createLeagueUseCase;

    @Autowired
    private GetLeagueByIdUseCase getLeagueByIdUseCase;

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody LeagueEntity league) {
        try {
            var result = this.createLeagueUseCase.execute(league);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable UUID id) {
        try {
            LeagueEntity league = getLeagueByIdUseCase.execute(id);
            return ResponseEntity.ok(league);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
