package com.example.demo.mappers.impl;

import com.example.demo.dtos.CompteDto;
import com.example.demo.entities.Compte;
import com.example.demo.mappers.CompteMapper;
import org.springframework.stereotype.Component;

@Component
public class CompteMapperImpl implements CompteMapper {
    @Override
    public CompteDto toDto(Compte compte) {
        CompteDto compteDto = new CompteDto();
        compteDto.setId(compte.getId());
        compteDto.setUsername(compte.getUsername());
        compteDto.setPassword(compte.getPassword());
        compteDto.setRole(compte.getRole());
        compteDto.setActiver(compte.isActiver());
        return compteDto;
    }

    @Override
    public Compte toEntity(CompteDto compteDto) {
        Compte compte = new Compte();
        compte.setId(compteDto.getId());
        compte.setUsername(compteDto.getUsername());
        compte.setPassword(compteDto.getPassword());
        compte.setRole(compteDto.getRole());
        compte.setActiver(compteDto.isActiver());
        return compte;
    }
}