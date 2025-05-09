package com.example.demo.services.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.dtos.PizzaDto;
import com.example.demo.dtos.StandardDto;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Pizza;
import com.example.demo.entities.Standard;
import com.example.demo.mappers.PizzaMapperImpl;
import com.example.demo.mappers.StandardMapperImpl;
import com.example.demo.repositories.PizzaRepository;
import com.example.demo.repositories.StandardRepository;
import com.example.demo.services.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardRepository standardRepository;
    @Autowired
    private StandardMapperImpl standardMapperImpl;
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private PizzaMapperImpl pizzaMapperImpl;

    @Override
    public StandardDto addStandard(StandardDto standardDto) {
        Standard standard = standardMapperImpl.toEntity(standardDto);
        Pizza pizza = standard.getPizza();
        float prix = 0;
        for (Ingredient ingredient : standard.getIngredients()) {
            prix += ingredient.getPrix();
        }
        pizza.setPrix(prix);
        Pizza savedPizza = this.pizzaRepository.save(pizza);
        standard.getPizza().setId(savedPizza.getId());
        Standard savedStandard = standardRepository.save(standard);
        return standardMapperImpl.toDto(savedStandard);
    }

    @Override
    public boolean deleteStandard(Long id) {
        standardRepository.deleteById(id);
        Optional<Standard> standard = standardRepository.findById(id);
        if (standard.isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public StandardDto updateStandard(Long id, StandardDto standardDto) {
        Optional<Standard> optionalStandard = standardRepository.findById(id);
        if (optionalStandard.isPresent()) {
            Standard standardToUpdate = standardMapperImpl.toEntity(standardDto);
            Standard standardUpdated = optionalStandard.get();
            standardUpdated.setIngredients(standardToUpdate.getIngredients());
            Pizza pizza = standardToUpdate.getPizza();
            float prix = 0;
            for (Ingredient ingredient : standardToUpdate.getIngredients()) {
                prix += ingredient.getPrix();
            }
            pizza.setPrix(prix);
            this.pizzaRepository.save(pizza);
            standardUpdated.setPizza(pizza);

            Standard savedStandard = standardRepository.save(standardUpdated);
            return standardMapperImpl.toDto(savedStandard);
        }
        return null;
    }

    @Override
    public StandardDto getStandardById(Long id) {
        Optional<Standard> optionalStandard = standardRepository.findById(id);
        return optionalStandard.isPresent() ? standardMapperImpl.toDto(optionalStandard.get()) : null;
    }

    @Override
    public StandardDto getStandardByPizzaId(Long id) {
        Optional<Pizza> pizza = this.pizzaRepository.findById(Math.toIntExact(id));
        if (!pizza.isPresent()) {
            return null;
        }
        List<StandardDto> standardDto = this.getAllStandards();
        for (StandardDto standard : standardDto) {
            if (standard.getPizza().equals(this.pizzaMapperImpl.toDto(pizza.get()))) {
                return standard;
            }
        }
        return null;
    }

    @Override
    public List<StandardDto> getAllStandards() {
        List<Standard> standards = standardRepository.findAll();
        List<StandardDto> standardDtos = new ArrayList<>();
        for (Standard standard : standards) {
            standardDtos.add(standardMapperImpl.toDto(standard));
        }
        return standardDtos;
    }
}