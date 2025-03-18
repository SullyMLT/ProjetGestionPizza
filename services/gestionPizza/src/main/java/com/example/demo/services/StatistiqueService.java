package com.example.demo.services;

import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.dtos.StatistiqueDto;

public interface StatistiqueService {
    StatistiqueDto getStatistique();
    StatistiqueDto updateStatistique(PizzaCommandeDto pizzaCommandeDto);

}
