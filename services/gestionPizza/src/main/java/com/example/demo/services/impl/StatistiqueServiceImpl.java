package com.example.demo.services.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.dtos.StatistiqueDto;
import com.example.demo.entities.Statistique;
import com.example.demo.mappers.StatistiqueMapper;
import com.example.demo.repositories.StatistiqueRepository;
import com.example.demo.services.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatistiqueServiceImpl implements StatistiqueService {

    @Autowired
    private StatistiqueRepository statistiqueRepository;
    @Autowired
    private StatistiqueMapper statistiqueMapper;

    @Override
    public StatistiqueDto getStatistique() {
        Statistique statistique = statistiqueRepository.findById(1L).orElse(new Statistique());
        return statistiqueMapper.toDto(statistique);
    }

    @Override
    public StatistiqueDto updateStatistique(PizzaCommandeDto pizzaCommandeDto) {
        Statistique statistique = statistiqueRepository.findById(1L).orElse(new Statistique());
        Map<Long, Integer> mapPizza = statistique.getStatPizza();
        Map<Long, Integer> mapIngredient = statistique.getStatIngredient();

        if (mapPizza.containsKey(pizzaCommandeDto.getPizza().getId())) {
            mapPizza.put(pizzaCommandeDto.getPizza().getId(), mapPizza.get(pizzaCommandeDto.getPizza().getId()) + 1);
        } else {
            mapPizza.put(pizzaCommandeDto.getPizza().getId(), 1);
        }
        for (IngredientDto ingre : pizzaCommandeDto.getIngredients()) {
            long id = ingre.getId();
            if (mapIngredient.containsKey(id)) {
                mapIngredient.put(id, mapIngredient.get(id) + 1);
            } else {
                mapIngredient.put(id, 1);
            }
        }

        statistique = statistiqueRepository.save(statistique);
        return statistiqueMapper.toDto(statistique);
    }
}