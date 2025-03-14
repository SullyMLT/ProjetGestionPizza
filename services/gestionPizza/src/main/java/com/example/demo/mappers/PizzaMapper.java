package com.example.demo.mappers;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Pizza;

public interface PizzaMapper {
    PizzaDto toDto(Pizza pizza);
    Pizza toEntity(PizzaDto pizzaDto);
}