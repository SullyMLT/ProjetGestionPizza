package com.example.demo.mappers;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Pizza;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PizzaMapper {

    @Mapping(source = "id", target = "id")
    PizzaDto toDto(Pizza pizza);

    @Mapping(source = "id", target = "id")
    Pizza toEntity(PizzaDto pizzaDto);
}