package com.example.demo.mappers.impl;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.dtos.CompteDto;
import com.example.demo.entities.Commande;
import com.example.demo.entities.Compte;
import com.example.demo.mappers.CompteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompteMapperImpl implements CompteMapper {

    @Autowired
    private CommandeMapperImpl commandeMapperImpl;

    @Override
    public CompteDto toDto(Compte compte) {
        CompteDto compteDto = new CompteDto();
        compteDto.setId(compte.getId());
        compteDto.setUsername(compte.getUsername());
        compteDto.setPassword(compte.getPassword());
        compteDto.setRole(compte.getRole());
        compteDto.setActiver(compte.isActiver());
        List<CommandeDto> commandesDto = new ArrayList<>();
        if (compte.getCommandes() != null) {
            for (Commande commande : compte.getCommandes()) {
                CommandeDto commandeDto = commandeMapperImpl.toDto(commande);
                commandesDto.add(commandeDto);
            }
        }
        compteDto.setCommandes(commandesDto);
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
        List<Commande> commandes = new ArrayList<>();
        if (compteDto.getCommandes() != null) {
            for (CommandeDto commandeDto : compteDto.getCommandes()) {
                Commande commande = commandeMapperImpl.toEntity(commandeDto);
                commandes.add(commande);
            }
        }
        compte.setCommandes(commandes);

        return compte;
    }
}