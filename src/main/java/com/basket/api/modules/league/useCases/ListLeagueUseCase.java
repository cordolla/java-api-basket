package com.basket.api.modules.league.useCases;

import com.basket.api.modules.league.entity.LeagueEntity;
import com.basket.api.modules.league.records.LeagueResponseDTO;
import com.basket.api.modules.league.repository.LeagueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListLeagueUseCase {

    private final LeagueRepository leagueRepository;

    public ListLeagueUseCase(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public List<LeagueResponseDTO> execute(){
        List<LeagueEntity> leagues = leagueRepository.findAll();

        return leagues.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LeagueResponseDTO convertToDTO(LeagueEntity league) {

        return new LeagueResponseDTO(
                league.getId(),
                league.getName(),
                league.getDescription(),
                league.getLogoUrl(),
                league.getStartDate() != null ? new java.sql.Date(league.getStartDate().getTime()).toLocalDate() : null,
                league.getEndDate() != null ? new java.sql.Date(league.getEndDate().getTime()).toLocalDate() : null
        );
    }
}
