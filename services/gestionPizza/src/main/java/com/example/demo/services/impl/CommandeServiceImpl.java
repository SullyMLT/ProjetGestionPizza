package com.example.demo.services.impl;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.Commande;
import com.example.demo.entities.Compte;
import com.example.demo.entities.PizzaCommande;
import com.example.demo.mappers.CommandeMapperImpl;
import com.example.demo.mappers.PizzaCommandeMapperImpl;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.repositories.CompteRepository;
import com.example.demo.repositories.PizzaCommandeRepository;
import com.example.demo.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommandeServiceImpl implements CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private CommandeMapperImpl commandeMapperImpl;
    @Autowired
    private PizzaCommandeRepository pizzaCommandeRepository;
    @Autowired
    private PizzaCommandeMapperImpl pizzaCommandeMapperImpl;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private StatistiqueServiceImpl statistiqueServiceImpl;

    @Override
    public CommandeDto addCommande(CommandeDto commandeDto) {
        Commande commande = this.commandeMapperImpl.toEntity(commandeDto);
        List<Commande> commandes = this.commandeRepository.findAll();
        if (commandes.isEmpty()) {
            for (Commande c : commandes) {
                if (c.getCompteId().equals(commandeDto.getCompteId()) && !c.isValidation()) {
                    return this.commandeMapperImpl.toDto(c);
                }
            }
        }
        Commande savedCommande = commandeRepository.save(commande);
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
    public CommandeDto validateCommande(Long commandeId) {
        Optional<Commande> optionalCommande = this.commandeRepository.findById(Math.toIntExact(commandeId));
        if (optionalCommande.isPresent()) {
            Commande commande = optionalCommande.get();
            commande.setValidation(true);
            Commande updatedCommande = commandeRepository.save(commande);
            List<PizzaCommande> pizzaCommandes = this.pizzaCommandeRepository.findAll();
            List<PizzaCommande> pizzaCommandes2 = new ArrayList<>();
            for (PizzaCommande pizzaCommande : pizzaCommandes) {
                if (Objects.equals(pizzaCommande.getCommandeId(), commandeId)) {
                    pizzaCommandes2.add(pizzaCommande);
                }
            }
            List<PizzaCommandeDto> pizzaCommandeDtos = new ArrayList<>();
            for (PizzaCommande pizzaCommande : pizzaCommandes2) {
                pizzaCommandeDtos.add(this.pizzaCommandeMapperImpl.toDto(pizzaCommande));
            }
            this.statistiqueServiceImpl.updateStatistiqueList(pizzaCommandeDtos);
            return this.commandeMapperImpl.toDto(updatedCommande);
        } else {
            System.out.println("problème sur la validation de la commande : "+ commandeId);
            return null;
        }
    }


}