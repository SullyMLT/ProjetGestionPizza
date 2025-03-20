package com.example.demo.services.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.dtos.StatistiqueDto;
import com.example.demo.entities.Standard;
import com.example.demo.entities.Statistique;
import com.example.demo.mappers.StatistiqueMapper;
import com.example.demo.repositories.StatistiqueRepository;
import com.example.demo.services.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatistiqueServiceImpl implements StatistiqueService {

    @Autowired
    private StatistiqueRepository statistiqueRepository;
    @Autowired
    private StatistiqueMapper statistiqueMapper;

    @Override
    public StatistiqueDto createStatistique(StatistiqueDto statistiqueDto) {
        Statistique statistique = this.statistiqueRepository.save(statistiqueMapper.toEntity(statistiqueDto));
        if (statistique.getId().equals("null")) {
            statistique.setId("1");
            statistique = this.statistiqueRepository.save(statistique);
        }
        return statistiqueMapper.toDto(statistique);
    }

    @Override
    public StatistiqueDto getStatistique() {
        Statistique statistique = statistiqueRepository.findById("1").orElse(new Statistique());
        return statistiqueMapper.toDto(statistique);
    }

    @Override
    public StatistiqueDto updateStatistiqueList(List<PizzaCommandeDto> pizzaCommandeDtos) {
        Statistique statistiqueNotFound = new Statistique();
        statistiqueNotFound.setId("1");
        Statistique statistique = statistiqueRepository.findById("1").orElse(statistiqueNotFound);
        Map<String, Integer> mapPizza = statistique.getStatPizza();
        Map<String, Integer> mapIngredient = statistique.getStatIngredient();
        for (PizzaCommandeDto pizzaCommandeDto : pizzaCommandeDtos) {
            if (mapPizza.containsKey(pizzaCommandeDto.getPizza().getNom())) {
                mapPizza.put(pizzaCommandeDto.getPizza().getNom(), mapPizza.get(pizzaCommandeDto.getPizza().getNom()) + 1);
            } else {
                mapPizza.put(pizzaCommandeDto.getPizza().getNom(), 1);
            }
            for (IngredientDto ingre : pizzaCommandeDto.getIngredients()) {
                String ingreName = ingre.getName();
                if (mapIngredient.containsKey(ingreName)) {
                    mapIngredient.put(ingreName, mapIngredient.get(ingreName) + 1);
                } else {
                    mapIngredient.put(ingreName, 1);
                }
            }
        }
        statistique = statistiqueRepository.save(statistique);
        return statistiqueMapper.toDto(statistique);
    }
}