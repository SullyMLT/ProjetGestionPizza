package com.example.demo.mappers.impl;

import com.example.demo.dtos.StatistiqueDto;
import com.example.demo.entities.Statistique;
import com.example.demo.mappers.StatistiqueMapper;
import org.springframework.stereotype.Component;

@Component
public class StatistiqueMapperImpl implements StatistiqueMapper {

    @Override
    public StatistiqueDto toDto(Statistique statistique) {
        if (statistique == null) {
            return null;
        }

        StatistiqueDto statistiqueDto = new StatistiqueDto();
        statistiqueDto.setId(statistique.getId());
        statistiqueDto.setStatPizza(statistique.getStatPizza());
        statistiqueDto.setStatIngredient(statistique.getStatIngredient());

        return statistiqueDto;
    }

    @Override
    public Statistique toEntity(StatistiqueDto statistiqueDto) {
        if (statistiqueDto == null) {
            return null;
        }

        Statistique statistique = new Statistique();
        statistique.setId(statistiqueDto.getId());
        statistique.setStatPizza(statistiqueDto.getStatPizza());
        statistique.setStatIngredient(statistiqueDto.getStatIngredient());

        return statistique;
    }
}