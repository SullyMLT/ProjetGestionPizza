package com.example.demo.mappers;

import com.example.demo.dtos.CompteDto;
import com.example.demo.entities.Compte;

public interface CompteMapper {
    CompteDto toDto(Compte compte);
    Compte toEntity(CompteDto compteDto);
}