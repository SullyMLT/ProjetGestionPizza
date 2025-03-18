package com.example.demo.services;

import com.example.demo.dtos.CommandeDto;

import java.util.List;

public interface CommandeService {
    CommandeDto addCommande(CommandeDto commandeDto, Long compteId);
    CommandeDto getCommandeById(Long id);
    List<CommandeDto> getAllCommandes();
    CommandeDto updateCommande(Long id, float prix);
    CommandeDto validateCommande(long id);
}
