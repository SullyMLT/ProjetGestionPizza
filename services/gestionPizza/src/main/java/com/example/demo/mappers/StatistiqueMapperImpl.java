package com.example.demo.mappers;

import com.example.demo.dtos.StatistiqueDto;
import com.example.demo.entities.Statistique;
import org.springframework.stereotype.Component;

@Component
public class StatistiqueMapperImpl {

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
