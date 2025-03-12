package com.example.demo.services;

import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.PizzaCommande;

import java.util.List;

public interface PizzaCommandeService {
    PizzaCommandeDto createPizzaCommande(PizzaCommandeDto pizzaCommande);
    PizzaCommandeDto getPizzaCommandeById(long id);
    List<PizzaCommandeDto> getAllPizzaCommandes();
    void deletePizzaCommande(long id);
}