package com.example.demo.services;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Pizza;

import java.util.List;

public interface PizzaService {
    List<PizzaDto> getAllPizzas();
    PizzaDto getPizzaById(Long id);
    PizzaDto addPizza(Pizza pizza);
    PizzaDto updatePizza(Long id, Pizza pizza);
    void deletePizza(Long id);
}
