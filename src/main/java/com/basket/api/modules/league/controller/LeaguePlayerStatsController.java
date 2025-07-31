package com.basket.api.modules.league.controller;

import com.basket.api.modules.league.records.ThreePointLeaderDTO;
import com.basket.api.modules.league.records.TopScorerDTO;
import com.basket.api.modules.league.useCases.GetThreePointLeadersUseCase;
import com.basket.api.modules.league.useCases.GetTopScorersUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/leagues/{leagueId}/player-stats")
@Tag(name = "Rankings de Jogadores da Liga", description = "Endpoints para obter os rankings de jogadores")
@SecurityRequirement(name = "bearer-key")
public class LeaguePlayerStatsController {

    private final GetTopScorersUseCase getTopScorersUseCase;
    private final GetThreePointLeadersUseCase getThreePointLeadersUseCase;

    public LeaguePlayerStatsController(GetTopScorersUseCase getTopScorersUseCase, GetThreePointLeadersUseCase getThreePointLeadersUseCase) {
        this.getTopScorersUseCase = getTopScorersUseCase;
        this.getThreePointLeadersUseCase = getThreePointLeadersUseCase;
    }

    @GetMapping("/top-scorers")
    public ResponseEntity<List<TopScorerDTO>> getTopScorers(@PathVariable UUID leagueId) {
        List<TopScorerDTO> topScorers = getTopScorersUseCase.execute(leagueId);
        return ResponseEntity.ok(topScorers);
    }

    @GetMapping("/three-point-leaders")
    @Operation(summary = "Busca o ranking de cestinhas de 3 pontos da liga (paginado)",
            description = "Retorna uma lista paginada dos jogadores com mais cestas de 3 pontos. Use os parâmetros 'page' (começando em 0) e 'size' para navegar.")
    public ResponseEntity<Page<ThreePointLeaderDTO>> getThreePointLeaders(
            @PathVariable UUID leagueId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ThreePointLeaderDTO> threePointLeadersPage = getThreePointLeadersUseCase.execute(leagueId, pageRequest);
        return ResponseEntity.ok(threePointLeadersPage);
    }
}