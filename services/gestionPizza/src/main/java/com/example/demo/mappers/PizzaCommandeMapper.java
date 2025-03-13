package com.example.demo.mappers;

import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.PizzaCommande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PizzaCommandeMapper {

    @Mapping(source = "commande.id", target = "commandeId")
    PizzaCommandeDto toDto(PizzaCommande pizzaCommande);

    @Mapping(source = "commandeId", target = "commande.id")
    PizzaCommande toEntity(PizzaCommandeDto pizzaCommandeDto);
}