package com.example.demo.services;

import com.example.demo.dtos.PizzaDto;
import java.util.List;

public interface PizzaService {
    List<PizzaDto> getAllPizzas();
    PizzaDto getPizzaById(Long id);
    PizzaDto addPizza(PizzaDto pizzaDto);
    PizzaDto updatePizza(Long id, PizzaDto pizzaDto);
    void deletePizza(Long id);
}
