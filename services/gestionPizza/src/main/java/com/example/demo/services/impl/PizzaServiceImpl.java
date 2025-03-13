package com.example.demo.services.impl;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Pizza;
import com.example.demo.mappers.PizzaMapper;
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
    private PizzaMapper pizzaMapper;

    @Override
    public List<PizzaDto> getAllPizzas() {
        List<Pizza> pizzas = pizzaRepository.findAll();
        List<PizzaDto> pizzasDto = new ArrayList<PizzaDto>();
        for (Pizza pizza : pizzas) {
            pizzasDto.add(this.pizzaMapper.toDto(pizza));
        }
        return pizzasDto;
    }

    @Override
    public PizzaDto getPizzaById(Long id) {
        Optional<Pizza> pizza = pizzaRepository.findById(Math.toIntExact(id));
        return pizza.isPresent() ? this.pizzaMapper.toDto(pizza.get()) : null;
    }

    @Override
    public PizzaDto addPizza(PizzaDto pizzaDto) {
        Pizza pizza = this.pizzaMapper.toEntity(pizzaDto);

        Pizza piz = pizzaRepository.save(pizza);
        return this.pizzaMapper.toDto(piz);
    }

    @Override
    public PizzaDto updatePizza(Long id, PizzaDto pizzaDto) {
        Optional <Pizza> pizza = this.pizzaRepository.findById(Math.toIntExact(id));
        if (pizza.isPresent()) {
            Pizza p = pizza.get();
            p.setNom(pizzaDto.getNom());
            p.setDescription(pizzaDto.getDescription());
            p.setPhoto(pizzaDto.getPhoto());
            p.setPrix(pizzaDto.getPrix());
            Pizza piz = pizzaRepository.save(p);
            return this.pizzaMapper.toDto(piz);
        } else {
            return null;
        }
    }

    @Override
    public void deletePizza(Long id) {
        pizzaRepository.deleteById(Math.toIntExact(id));
    }
}