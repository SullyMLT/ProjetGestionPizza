package com.example.demo.services;

import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.dtos.StatistiqueDto;

import java.util.List;

public interface StatistiqueService {
    StatistiqueDto getStatistique();
    StatistiqueDto updateStatistiqueList(List<PizzaCommandeDto> pizzaCommandeDto);
}
