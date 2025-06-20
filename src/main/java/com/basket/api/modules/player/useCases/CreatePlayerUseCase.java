package com.basket.api.modules.player.useCases;

import com.basket.api.modules.player.entity.PlayerEntity;
import com.basket.api.modules.player.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePlayerUseCase {

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerEntity execute(PlayerEntity playerEntity) {
        if(playerRepository.findByDocument(playerEntity.getDocument()).isPresent()) {
            throw new Error("Document already exists");
        }
        return playerRepository.save(playerEntity);
    }
}
