package com.example.demo.services;

import com.example.demo.dtos.PizzaCommandeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PizzaCommandeService {
    PizzaCommandeDto createPizzaCommande(PizzaCommandeDto pizzaCommande);
    PizzaCommandeDto getPizzaCommandeById(long id);
    List<PizzaCommandeDto> getAllPizzaCommandes();
    void deletePizzaCommande(long id);
    List<PizzaCommandeDto> getPizzaCommandeByCommandeId(long id);
}