package com.example.demo.mappers;

import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.PizzaCommande;

public interface PizzaCommandeMapper {
    PizzaCommandeDto toDto(PizzaCommande pizzaCommande);
    PizzaCommande toEntity(PizzaCommandeDto pizzaCommandeDto);
}