package com.example.demo.services.impl;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.entities.Commande;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpl implements CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    public CommandeDto addCommande(Commande commande) {
        commandeRepository.save(commande);
        return new CommandeDto(commande);
    }

    public List<CommandeDto> getAllCommandes() {
        List<Commande> commandes = commandeRepository.findAll();
        List<CommandeDto> commandeDtos = new ArrayList<>();

        if (commandes.isEmpty()) {
            return null;
        }else {
            for (Commande commande : commandes) {
                commandeDtos.add(new CommandeDto(commande));
            }
            return commandeDtos;
        }
    }

    public CommandeDto getCommandeById(Long id) {
        Optional<Commande> optionalCommande = commandeRepository.findById(Math.toIntExact(id));
        if (optionalCommande.isPresent()) {
            CommandeDto commandeDtos = new CommandeDto(optionalCommande.get());
            return commandeDtos;
        }else{
            System.out.println("get commandeById raté");
            return null;
        }
    }
}