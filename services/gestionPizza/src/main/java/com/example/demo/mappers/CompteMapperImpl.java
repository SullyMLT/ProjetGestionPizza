package com.example.demo.mappers;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.dtos.CompteDto;
import com.example.demo.entities.Commande;
import com.example.demo.entities.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompteMapperImpl {

    @Autowired
    private CommandeMapperImpl commandeMapperImpl;


    public CompteDto toDto(Compte compte) {
        CompteDto compteDto = new CompteDto();
        compteDto.setId(compte.getId());
        compteDto.setUsername(compte.getUsername());
        compteDto.setPassword(compte.getPassword());
        compteDto.setRole(compte.getRole());
        compteDto.setActiver(compte.isActiver());
        return compteDto;
    }


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