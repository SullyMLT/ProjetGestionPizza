package com.example.demo.services.impl;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Pizza;
import com.example.demo.mappers.impl.PizzaMapperImpl;
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
    @Autowired
    private PizzaMapperImpl pizzaMapperImpl;

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
            p.setNom(pizzaDto.getNom());
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
    public void deletePizza(Long id) {
        this.pizzaRepository.deleteById(Math.toIntExact(id));
    }
}