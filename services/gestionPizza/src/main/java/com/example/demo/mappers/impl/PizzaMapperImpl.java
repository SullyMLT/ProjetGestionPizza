package com.example.demo.mappers.impl;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Pizza;
import com.example.demo.entities.Standard;
import com.example.demo.mappers.PizzaMapper;
import com.example.demo.mappers.StandardMapper;
import com.example.demo.repositories.StandardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PizzaMapperImpl implements PizzaMapper {

    @Autowired
    private StandardRepository standardRepository;

    public PizzaDto toDto(Pizza pizza){
        PizzaDto pizzaDto = new PizzaDto();
        pizzaDto.setId(pizza.getId());
        pizzaDto.setNom(pizza.getNom());
        pizzaDto.setPrix(pizza.getPrix());
        pizzaDto.setDescription(pizza.getDescription());
        pizzaDto.setPhoto(pizza.getPhoto());
        pizzaDto.setStandardId(pizza.getStandard().getId());
        return pizzaDto;
    }
    public Pizza toEntity(PizzaDto pizzaDto) {
        Pizza pizza = new Pizza();
        pizza.setId(pizzaDto.getId());
        pizza.setNom(pizzaDto.getNom());
        pizza.setPrix(pizzaDto.getPrix());
        pizza.setDescription(pizzaDto.getDescription());
        pizza.setPhoto(pizzaDto.getPhoto());
        Optional<Standard> pizzaOptional = standardRepository.findById(pizzaDto.getStandardId());
        if (pizzaOptional.isPresent()) {
            pizza.setStandard(pizzaOptional.get());
        }else{
            pizza.setStandard(null);
        }
        return pizza;
    }
}