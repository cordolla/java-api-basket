package com.basket.api.modules.gameEvent.useCases;

import com.basket.api.modules.gameEvent.records.GameEventRequestDTO;
import com.basket.api.modules.gameEvent.entity.GameEventEntity;
import com.basket.api.modules.gameEvent.repository.GameEventRepository;
import com.basket.api.modules.team.entity.TeamEntity;
import com.basket.api.modules.team.repository.TeamRepository;
import com.basket.api.modules.game.entity.GameEntity;
import com.basket.api.modules.game.repository.GameRepository;
import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.player.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class AddGameEventUseCase {

    private final GameEventRepository gameEventRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final  TeamRepository teamRepository;

    public AddGameEventUseCase(GameEventRepository gameEventRepository, GameRepository gameRepository, PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.gameEventRepository = gameEventRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public GameEventEntity execute(GameEventRequestDTO gameEventRequestDTO) {
        GameEntity game = gameRepository.findById(gameEventRequestDTO.gameId())
                .orElseThrow(() -> new RuntimeException("Game not found"));

        TeamEntity team = teamRepository.findById(gameEventRequestDTO.teamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        PlayerEntity player = null;
        if (gameEventRequestDTO.playerId() != null) {
            player = playerRepository.findById(gameEventRequestDTO.playerId())
                    .orElseThrow(() -> new RuntimeException("Player not found"));
        }

        GameEventEntity event = new GameEventEntity();
        event.setGame(game);
        event.setTeam(team);
        event.setPlayer(player);
        event.setEventType(gameEventRequestDTO.eventType());
        event.setEventTime(gameEventRequestDTO.eventTime());
        event.setPoints(gameEventRequestDTO.points());

        return gameEventRepository.save(event);
    }
}
