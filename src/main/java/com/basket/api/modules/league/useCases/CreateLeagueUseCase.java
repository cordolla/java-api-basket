package com.basket.api.modules.league.useCases;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.exception.ResourceNotFoundException;
import com.basket.api.modules.category.repository.CategoryRepository;
import com.basket.api.modules.league.entity.LeagueEntity;
import com.basket.api.modules.league.records.LeagueRequestDTO;
import com.basket.api.modules.league.repository.LeagueRepository;
import com.basket.api.modules.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateLeagueUseCase {


    private final LeagueRepository leagueRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public CreateLeagueUseCase(LeagueRepository leagueRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.leagueRepository = leagueRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public LeagueEntity execute(LeagueRequestDTO leagueRequestDTO) {
        if (leagueRepository.findByName(leagueRequestDTO.name()).isPresent()) {
            throw new BusinessRuleException("League with name '" + leagueRequestDTO.name() + "' already exists.");
        }

        var user = userRepository.findById(leagueRequestDTO.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var category = categoryRepository.findById(leagueRequestDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        LeagueEntity leagueEntity = new LeagueEntity();
        leagueEntity.setName(leagueRequestDTO.name());
        leagueEntity.setDescription(leagueRequestDTO.description());
        leagueEntity.setLogoUrl(leagueRequestDTO.logoUrl());

        leagueEntity.setUser(user);
        leagueEntity.setCategory(category);

        return leagueRepository.save(leagueEntity);
    }
}
