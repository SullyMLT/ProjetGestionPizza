package com.example.demo.services;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.entities.Commande;

import java.util.List;

public interface CommandeService {
    public CommandeDto addCommande(CommandeDto commandeDto);
    public CommandeDto getCommandeById(Long id);
    public List<CommandeDto> getAllCommandes();
    CommandeDto updateCommande(Long id, Commande commandePizza);
}
