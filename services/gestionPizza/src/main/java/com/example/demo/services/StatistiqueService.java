package com.example.demo.services;

import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.dtos.StatistiqueDto;

import java.util.List;

public interface StatistiqueService {
    StatistiqueDto createStatistique(StatistiqueDto statistiqueDto);
    StatistiqueDto getStatistique();
    StatistiqueDto updateStatistiqueList(List<PizzaCommandeDto> pizzaCommandeDto);
}
