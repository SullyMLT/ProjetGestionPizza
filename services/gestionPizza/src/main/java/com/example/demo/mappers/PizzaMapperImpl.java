package com.example.demo.mappers;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Pizza;
import org.springframework.stereotype.Component;

@Component
public class PizzaMapperImpl {

    public PizzaDto toDto(Pizza pizza){
        PizzaDto pizzaDto = new PizzaDto();
        if (pizza == null) {
            return null;
        }
        pizzaDto.setId(pizza.getId());
        pizzaDto.setNom(pizza.getNom());
        pizzaDto.setPrix(pizza.getPrix());
        pizzaDto.setDescription(pizza.getDescription());
        pizzaDto.setPhoto(pizza.getPhoto());
        pizzaDto.setActiver(pizza.isActiver());
        return pizzaDto;
    }

    public Pizza toEntity(PizzaDto pizzaDto) {
        Pizza pizza = new Pizza();
        if (pizzaDto == null) {
            return null;
        }
        pizza.setNom(pizzaDto.getNom());
        pizza.setPrix(pizzaDto.getPrix());
        pizza.setDescription(pizzaDto.getDescription());
        pizza.setPhoto(pizzaDto.getPhoto());
        pizza.setActiver(pizzaDto.isActiver());
        return pizza;
    }
}