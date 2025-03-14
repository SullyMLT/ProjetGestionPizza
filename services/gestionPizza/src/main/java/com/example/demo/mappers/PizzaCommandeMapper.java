package com.example.demo.mappers;

import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.PizzaCommande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PizzaMapper.class})
public interface PizzaCommandeMapper {

    @Mapping(source = "pizza", target = "pizzaDto")
     PizzaCommandeDto toDto(PizzaCommande pizzaCommande);

    @Mapping(source = "pizzaDto", target = "pizza")
    PizzaCommande toEntity(PizzaCommandeDto pizzaCommandeDto);
}