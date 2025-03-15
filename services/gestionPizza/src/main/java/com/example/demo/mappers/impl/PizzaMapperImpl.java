package com.example.demo.mappers.impl;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.dtos.StandardDto;
import com.example.demo.entities.Pizza;
import com.example.demo.mappers.PizzaMapper;
import com.example.demo.services.impl.StandardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PizzaMapperImpl implements PizzaMapper {

    public PizzaDto toDto(Pizza pizza){
        PizzaDto pizzaDto = new PizzaDto();
        pizzaDto.setId(pizza.getId());
        pizzaDto.setNom(pizza.getNom());
        pizzaDto.setPrix(pizza.getPrix());
        pizzaDto.setDescription(pizza.getDescription());
        pizzaDto.setPhoto(pizza.getPhoto());
        return pizzaDto;
    }
    public Pizza toEntity(PizzaDto pizzaDto) {
        Pizza pizza = new Pizza();
        pizza.setId(pizzaDto.getId());
        pizza.setNom(pizzaDto.getNom());
        pizza.setPrix(pizzaDto.getPrix());
        pizza.setDescription(pizzaDto.getDescription());
        pizza.setPhoto(pizzaDto.getPhoto());
        return pizza;
    }
}