package com.basket.api.modules.league.useCases;

import com.basket.api.modules.league.entity.LeagueEntity;
import com.basket.api.modules.league.repository.LeagueRepository;
import com.basket.api.modules.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateLeagueUseCase {


    private final LeagueRepository leagueRepository;
    private final UserRepository userRepository;

    public CreateLeagueUseCase(LeagueRepository leagueRepository, UserRepository userRepository) {
        this.leagueRepository = leagueRepository;
        this.userRepository = userRepository;
    }

    public LeagueEntity execute(LeagueEntity leagueEntity) {
        if (leagueRepository.findByName(leagueEntity.getName()).isPresent()) {
            throw new RuntimeException("League with name '" + leagueEntity.getName() + "' already exists.");
        }

        var user = userRepository.findById(leagueEntity.getUser().getId()).orElseThrow(() -> new Error("User not found"));

        leagueEntity.setUser(user);

        return leagueRepository.save(leagueEntity);
    }
}
