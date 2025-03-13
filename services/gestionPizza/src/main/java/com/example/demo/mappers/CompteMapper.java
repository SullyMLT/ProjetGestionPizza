package com.example.demo.mappers;

import com.example.demo.dtos.CompteDto;
import com.example.demo.entities.Compte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompteMapper {

    @Mapping(source = "commandes", target = "commandeIds")
    CompteDto toDto(Compte compte);

    @Mapping(source = "commandeIds", target = "commandes")
    Compte toEntity(CompteDto compteDto);
}