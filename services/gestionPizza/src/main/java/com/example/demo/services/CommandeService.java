package com.example.demo.services;

import com.example.demo.dtos.CommandeDto;

import java.util.List;

public interface CommandeService {
    public CommandeDto addCommande(CommandeDto commandeDto, long compteId);
    public CommandeDto getCommandeById(Long id);
    public List<CommandeDto> getAllCommandes();
    CommandeDto updateCommande(Long id, float prix);
    CommandeDto validateCommande(long id);
}
