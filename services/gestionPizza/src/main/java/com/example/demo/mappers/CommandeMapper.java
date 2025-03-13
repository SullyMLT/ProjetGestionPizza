package com.example.demo.mappers;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.entities.Commande;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandeMapper {
    CommandeDto toDto(Commande commande);
    Commande toEntity(CommandeDto commandeDto);
}