package com.example.demo.services.impl;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.entities.Commande;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpl implements CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    public void addCommande(CommandeDto commandeDto) {
        Commande commande = commandeDto.toEntity();
        commandeRepository.save(commande);
    }

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public Optional<Commande> getCommandeById(Long id) {
        return commandeRepository.findById(Math.toIntExact(id));
    }

    public void updateCommande(Long id, CommandeDto updatedCommande) {
        Commande commande = updatedCommande.toEntity();
        if (commandeRepository.existsById(Math.toIntExact(id))) {
            commande.setId(id);
            commandeRepository.save(commande);
        }
    }

    public void deleteCommande(Long id) {
        commandeRepository.deleteById(Math.toIntExact(id));
    }
}