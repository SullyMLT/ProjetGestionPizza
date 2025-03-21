package com.example.demo.services.impl;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Pizza;
import com.example.demo.entities.Standard;
import com.example.demo.mappers.PizzaMapperImpl;
import com.example.demo.repositories.PizzaRepository;
import com.example.demo.repositories.StandardRepository;
import com.example.demo.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private PizzaMapperImpl pizzaMapperImpl;
    @Autowired
    private StandardRepository standardRepository;

    @Override
    public List<PizzaDto> getAllPizzas() {
        List<Pizza> pizzas = this.pizzaRepository.findAll();
        List<PizzaDto> pizzasDto = new ArrayList<PizzaDto>();
        for (Pizza pizza : pizzas) {
            pizzasDto.add(this.pizzaMapperImpl.toDto(pizza));
        }
        return pizzasDto;
    }

    @Override
    public PizzaDto getPizzaById(Long id) {
        Optional<Pizza> pizza = this.pizzaRepository.findById(Math.toIntExact(id));
        return pizza.isPresent() ? this.pizzaMapperImpl.toDto(pizza.get()) : null;
    }

    @Override
    public PizzaDto addPizza(PizzaDto pizzaDto) {
        Pizza pizza = this.pizzaMapperImpl.toEntity(pizzaDto);
        pizza.setActiver(true);
        pizza.setPrix(0f);
        Pizza piz = this.pizzaRepository.save(pizza);
        return this.pizzaMapperImpl.toDto(piz);
    }

    @Override
    public PizzaDto updatePizza(Long id, PizzaDto pizzaDto) {
        Optional <Pizza> pizza = this.pizzaRepository.findById(Math.toIntExact(id));
        if (pizza.isPresent()) {
            // information sur l'entity à mettre à jour
            Pizza piz = this.pizzaMapperImpl.toEntity(pizzaDto);
            // entity to update
            Pizza p = pizza.get();
            // mise à jour des données
            p.setNom(piz.getNom());
            p.setDescription(piz.getDescription());
            p.setPhoto(piz.getPhoto());
            p.setPrix(piz.getPrix());

            Pizza savedPizza = this.pizzaRepository.save(p);
            return this.pizzaMapperImpl.toDto(savedPizza);
        } else {
            return null;
        }
    }

    @Override
    public boolean deletePizza(Long id) {
        Optional<Pizza> optionalPizza = this.pizzaRepository.findById(Math.toIntExact(id));
        if (optionalPizza.isEmpty()) {
            return false;
        }
        List<Standard> standard = this.standardRepository.findAll();
        for (Standard s : standard) {
            if (Objects.equals(s.getPizza().getId(), id)) {
                this.standardRepository.deleteById(s.getId());
                if (this.standardRepository.findById(s.getId()).isPresent()) {
                    return false;
                }
            }
        }
        Pizza pizza = optionalPizza.get();
        pizza.setActiver(false);
        pizzaRepository.save(pizza);
        return true;
    }
}