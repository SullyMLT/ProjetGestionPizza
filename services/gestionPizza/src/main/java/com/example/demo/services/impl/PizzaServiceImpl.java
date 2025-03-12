package com.example.demo.services.impl;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Pizza;
import com.example.demo.repositories.PizzaRepository;
import com.example.demo.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public List<PizzaDto> getAllPizzas() {
        List<Pizza> pizzas = pizzaRepository.findAll();
        List<PizzaDto> pizzasDto = new ArrayList<PizzaDto>();
        for (Pizza pizza : pizzas) {
            pizzasDto.add(new PizzaDto(pizza));
        }
        return pizzasDto;
    }

    @Override
    public PizzaDto getPizzaById(Long id) {
        Optional<Pizza> pizza = pizzaRepository.findById(Math.toIntExact(id));
        return pizza.isPresent() ? new PizzaDto(pizza.get()) : null;
    }

    @Override
    public PizzaDto addPizza(Pizza pizza) {
        Pizza piz = pizzaRepository.save(pizza);
        return new PizzaDto(piz);
    }

    @Override
    public PizzaDto updatePizza(Long id, Pizza pizza) {
        if (pizzaRepository.existsById(Math.toIntExact(id))) {
            pizza.setId(id);
            pizzaRepository.save(pizza);
            return new PizzaDto(pizza);
        }
        return null;
    }

    @Override
    public void deletePizza(Long id) {
        pizzaRepository.deleteById(Math.toIntExact(id));
    }
}