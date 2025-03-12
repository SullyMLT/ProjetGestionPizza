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

    @Override
    public CommandeDto addCommande(CommandeDto commandeDto) {
        Commande commande = new Commande();
        commande.setDescription(commandeDto.getDescription());
        commande.setPrix(commandeDto.getPrix());
        commande.setDate(commandeDto.getDate());
        commande.setValidation(commandeDto.getValidation());

        Commande savedCommande = commandeRepository.save(commande);
        return new CommandeDto(savedCommande);
    }

    @Override
    public List<CommandeDto> getAllCommandes() {
        List<Commande> commandes = commandeRepository.findAll();
        List<CommandeDto> commandeDtos = new ArrayList<>();

        if (commandes.isEmpty()) {
            return null;
        } else {
            for (Commande commande : commandes) {
                commandeDtos.add(new CommandeDto(commande));
            }
            return commandeDtos;
        }
    }

    @Override
    public CommandeDto getCommandeById(Long id) {
        Optional<Commande> optionalCommande = commandeRepository.findById(Math.toIntExact(id));
        if (optionalCommande.isPresent()) {
            return new CommandeDto(optionalCommande.get());
        } else {
            System.out.println("get commandeById failed");
            return null;
        }
    }

    @Override
    public CommandeDto updateCommande(Long id, Commande commandePizza) {
        Optional<Commande> optionalCommande = commandeRepository.findById(Math.toIntExact(id));
        if (optionalCommande.isPresent()) {
            Commande commande = optionalCommande.get();
            commande.setDescription(commandePizza.getDescription());
            commande.setPrix(commandePizza.getPrix());
            commande.setDate(commandePizza.getDate());
            commande.setValidation(commandePizza.getValidation());

            Commande updatedCommande = commandeRepository.save(commande);
            return new CommandeDto(updatedCommande);
        } else {
            System.out.println("update commande failed");
            return null;
        }
    }
}