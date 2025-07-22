package com.basket.api.modules.gameEvent.useCases;

import com.basket.api.modules.gameEvent.dto.GameEventRequestDTO;
import com.basket.api.modules.gameEvent.entity.GameEventEntity;
import com.basket.api.modules.gameEvent.repository.GameEventRepository;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.repository.TeamRepository;
import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.repository.GameRepository;
import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.player.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddGameEventUseCase {
    @Autowired
    private GameEventRepository gameEventRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    public GameEventEntity execute(GameEventRequestDTO gameEventRequestDTO) {
        GameEntity game = gameRepository.findById(gameEventRequestDTO.getGameId())
                .orElseThrow(() -> new RuntimeException("Game not found"));

        TeamEntity team = teamRepository.findById(gameEventRequestDTO.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        PlayerEntity player = null;
        if (gameEventRequestDTO.getPlayerId() != null) {
            player = playerRepository.findById(gameEventRequestDTO.getPlayerId())
                    .orElseThrow(() -> new RuntimeException("Player not found"));
        }

        GameEventEntity event = new GameEventEntity();
        event.setGame(game);
        event.setTeam(team);
        event.setPlayer(player);
        event.setEventType(gameEventRequestDTO.getEventType());
        event.setEventTime(gameEventRequestDTO.getEventTime());
        event.setPoints(gameEventRequestDTO.getPoints());

        return gameEventRepository.save(event);
    }
}
