package com.example.demo.services;

import com.example.demo.dtos.PizzaCommandeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PizzaCommandeService {
    PizzaCommandeDto createPizzaCommande(PizzaCommandeDto pizzaCommande);
    PizzaCommandeDto getPizzaCommandeById(Long id);
    List<PizzaCommandeDto> getAllPizzaCommandes();
    boolean deletePizzaCommande(Long id);
    List<PizzaCommandeDto> getPizzaCommandeByCommandeId(Long id);
}