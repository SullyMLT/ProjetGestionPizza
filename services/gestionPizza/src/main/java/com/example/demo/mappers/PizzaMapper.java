package com.example.demo.mappers;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Pizza;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaMapper {
    PizzaDto toDto(Pizza pizza);
    Pizza toEntity(PizzaDto pizzaDto);
}