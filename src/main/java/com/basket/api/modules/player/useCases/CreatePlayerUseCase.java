package com.basket.api.modules.player.useCases;

import com.basket.api.exception.BusinessRuleException;
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
            throw new BusinessRuleException("Document already exists: " + playerEntity.getDocument());
        }
        return playerRepository.save(playerEntity);
    }
}
