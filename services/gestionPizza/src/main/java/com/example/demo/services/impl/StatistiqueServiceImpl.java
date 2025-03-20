package com.example.demo.services.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.dtos.StatistiqueDto;
import com.example.demo.entities.Statistique;
import com.example.demo.mappers.StatistiqueMapperImpl;
import com.example.demo.repositories.StatistiqueRepository;
import com.example.demo.services.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StatistiqueServiceImpl implements StatistiqueService {

    @Autowired
    private StatistiqueRepository statistiqueRepository;
    @Autowired
    private StatistiqueMapperImpl statistiqueMapper;

    @Override
    public StatistiqueDto getStatistique() {
        Optional<Statistique> getStatistique = statistiqueRepository.findById("1");
        if (!getStatistique.isPresent()) {
            return null;
        }
        Statistique statistique = getStatistique.get();
        return statistiqueMapper.toDto(statistique);
    }

    @Override
    public StatistiqueDto updateStatistiqueList(List<PizzaCommandeDto> pizzaCommandeDtos) {
        Statistique statistiqueNotFound = new Statistique();
        statistiqueNotFound.setId("1");
        statistiqueNotFound.setStatPizza(new HashMap<>(0));
        statistiqueNotFound.setStatIngredient(new HashMap<>(0));
        Optional<Statistique> optionalStatistique = statistiqueRepository.findById("1");
        if (!optionalStatistique.isPresent()) {
            statistiqueRepository.save(statistiqueNotFound);
            return statistiqueMapper.toDto(statistiqueNotFound);
        }
        Statistique statistique = optionalStatistique.get();
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