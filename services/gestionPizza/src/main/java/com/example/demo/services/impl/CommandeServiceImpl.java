package com.example.demo.services.impl;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.dtos.CompteDto;
import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.Commande;
import com.example.demo.entities.Compte;
import com.example.demo.mappers.impl.CommandeMapperImpl;
import com.example.demo.mappers.impl.CompteMapperImpl;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.services.CommandeService;
import com.example.demo.services.PizzaCommandeService;
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
    @Autowired
    private CompteServiceImpl compteServiceImpl;
    @Autowired
    private CompteMapperImpl compteMapperImpl;
    @Autowired
    private PizzaCommandeService pizzaCommandeService;
    @Autowired
    private StatistiqueServiceImpl statistiqueServiceImpl;

    @Override
    public CommandeDto addCommande(CommandeDto commandeDto, Long compteId) {
        Commande commande = this.commandeMapperImpl.toEntity(commandeDto);
        CompteDto compte = compteServiceImpl.getCompteById(compteId);
        Compte compteEntity = compteMapperImpl.toEntity(compte);
        if (compteEntity.getCommandes() == null) {
            compteEntity.setCommandes(new ArrayList<>());
        }
        for (Commande c : compteEntity.getCommandes()) {
            if (!c.isValidation()) {
                return null;
            }
        }
        Commande savedCommande = commandeRepository.save(commande);
        compteEntity.getCommandes().add(savedCommande);
        CompteDto updatedCompte = compteMapperImpl.toDto(compteEntity);
        compteServiceImpl.updateCompte(compteId, updatedCompte);
        return this.commandeMapperImpl.toDto(savedCommande);
    }

    @Override
    public List<CommandeDto> getAllCommandes() {
        List<Commande> commandes = this.commandeRepository.findAll();
        List<CommandeDto> commandeDtos = new ArrayList<>();

        if (!commandes.isEmpty()) {
            for (Commande commande : commandes) {
                commandeDtos.add(this.commandeMapperImpl.toDto(commande));
            }
            return commandeDtos;
        } else {
            return null;
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
    public CommandeDto updateCommande(Long id, float prix) {
        Optional<Commande> optionalCommande = this.commandeRepository.findById(Math.toIntExact(id));
        if (optionalCommande.isPresent()) {
            Commande commande = optionalCommande.get();
            commande.setPrix(prix);
            Commande updatedCommande = commandeRepository.save(commande);
            return this.commandeMapperImpl.toDto(updatedCommande);
        } else {
            System.out.println("update commande failed");
            return null;
        }
    }

    @Override
    public CommandeDto validateCommande(long commandeId) {
        Optional<Commande> optionalCommande = this.commandeRepository.findById(Math.toIntExact(commandeId));
        if (optionalCommande.isPresent()) {
            Commande commande = optionalCommande.get();
            commande.setValidation(true);
            Commande updatedCommande = commandeRepository.save(commande);
            List<PizzaCommandeDto> pizzaCommandeDtos = new ArrayList<>();
            pizzaCommandeDtos = pizzaCommandeService.getPizzaCommandeByCommandeId(updatedCommande.getId());
            System.out.println(pizzaCommandeDtos);
            this.statistiqueServiceImpl.updateStatistiqueList(pizzaCommandeDtos);
            return this.commandeMapperImpl.toDto(updatedCommande);
        } else {
            System.out.println("problème sur la validation de la commande : "+ commandeId);
            return null;
        }
    }
}