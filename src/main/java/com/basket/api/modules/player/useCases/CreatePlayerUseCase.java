package com.basket.api.modules.player.useCases;

import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.player.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class CreatePlayerUseCase {

    private final PlayerRepository playerRepository;

    public CreatePlayerUseCase(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerEntity execute(PlayerEntity playerEntity) {
        if (playerRepository.findByDocument(playerEntity.getDocument()).isPresent()) {
            throw new Error("Document already exists");
        }
        return playerRepository.save(playerEntity);
    }
}
