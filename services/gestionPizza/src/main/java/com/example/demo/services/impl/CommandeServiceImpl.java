package com.example.demo.services.impl;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.entities.Commande;
import com.example.demo.mappers.impl.CommandeMapperImpl;
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

    @Autowired
    private CommandeMapperImpl commandeMapperImpl;

    @Override
    public CommandeDto addCommande(CommandeDto commandeDto) {
        Commande commande = this.commandeMapperImpl.toEntity(commandeDto);
        Commande savedCommande = commandeRepository.save(commande);
        return this.commandeMapperImpl.toDto(savedCommande);
    }

    @Override
    public List<CommandeDto> getAllCommandes() {
        List<Commande> commandes = this.commandeRepository.findAll();
        List<CommandeDto> commandeDtos = new ArrayList<>();

        if (commandes.isEmpty()) {
            return null;
        } else {
            for (Commande commande : commandes) {
                commandeDtos.add(this.commandeMapperImpl.toDto(commande));
            }
            return commandeDtos;
        }
    }

    @Override
    public CommandeDto getCommandeById(Long id) {
        Optional<Commande> optionalCommande = commandeRepository.findById(Math.toIntExact(id));
        if (optionalCommande.isPresent()) {
            return this.commandeMapperImpl.toDto(optionalCommande.get());
        } else {
            System.out.println("get commandeById failed");
            return null;
        }
    }

    @Override
    public CommandeDto updateCommande(Long id, CommandeDto commandeDto) {
        Optional<Commande> optionalCommande = this.commandeRepository.findById(Math.toIntExact(id));
        if (optionalCommande.isPresent()) {
            Commande commande = optionalCommande.get();
            Commande commandeToUpdate = this.commandeMapperImpl.toEntity(commandeDto);
            commande.setDate(commandeToUpdate.getDate());
            commande.setDescription(commandeToUpdate.getDescription());
            commande.setPrix(commandeToUpdate.getPrix());
            commande.setValidation(commandeToUpdate.getValidation());

            Commande updatedCommande = commandeRepository.save(commande);
            return this.commandeMapperImpl.toDto(updatedCommande);
        } else {
            System.out.println("update commande failed");
            return null;
        }
    }
}