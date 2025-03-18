package com.example.demo.services.impl;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.*;
import com.example.demo.mappers.impl.CommandeMapperImpl;
import com.example.demo.mappers.impl.PizzaCommandeMapperImpl;
import com.example.demo.repositories.*;
import com.example.demo.services.PizzaCommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaCommandeServiceImpl implements PizzaCommandeService {

    @Autowired
    private PizzaCommandeRepository pizzaCommandeRepository;
    @Autowired
    private PizzaCommandeMapperImpl pizzaCommandeMapperImpl;
    @Autowired
    private CommandeServiceImpl commandeServiceImpl;
    @Autowired
    private CommandeMapperImpl commandeMapperImpl;
    @Autowired
    private StatistiqueServiceImpl statistiqueServiceImpl;

    @Override
    public PizzaCommandeDto createPizzaCommande(PizzaCommandeDto pizzaCommandeDto) {
        PizzaCommande pizzaCommande = pizzaCommandeMapperImpl.toEntity(pizzaCommandeDto);
        Pizza pizza = pizzaCommande.getPizza();
        float prix = 0;
        if (pizzaCommande.getIngredients() != null) {
            for (Ingredient ingre : pizzaCommande.getIngredients()) {
                prix += ingre.getPrix();
            }
            pizza.setPrix(prix);
        }
        pizzaCommande.setPizza(pizza);
        PizzaCommande savedPizzaCommande = pizzaCommandeRepository.save(pizzaCommande);
        CommandeDto commandeDto = commandeServiceImpl.getCommandeById(pizzaCommande.getCommandeId());
        Commande commande = commandeMapperImpl.toEntity(commandeDto);
        commande.setPrix(commande.getPrix() + pizza.getPrix());
        commandeServiceImpl.updateCommande(pizzaCommande.getCommandeId(), commande.getPrix());
        PizzaCommandeDto pizzaComDto = pizzaCommandeMapperImpl.toDto(savedPizzaCommande);
        statistiqueServiceImpl.updateStatistique(pizzaComDto);
        return pizzaComDto;
    }

    @Override
    public PizzaCommandeDto getPizzaCommandeById(long id) {
        Optional<PizzaCommande> pizzaCommande = pizzaCommandeRepository.findById(id);
        if (!pizzaCommande.isPresent()) {
            return null;
        }
        return pizzaCommandeMapperImpl.toDto(pizzaCommande.get());
    }

    @Override
    public List<PizzaCommandeDto> getAllPizzaCommandes() {
        List<PizzaCommande> pizzaCommandes = this.pizzaCommandeRepository.findAll();
        List<PizzaCommandeDto> pizzaCommandeDtos = new ArrayList<>();
        for (PizzaCommande pizzaCommande : pizzaCommandes) {
            pizzaCommandeDtos.add(this.pizzaCommandeMapperImpl.toDto(pizzaCommande));
        }
        return pizzaCommandeDtos;
    }

    @Override
    public void deletePizzaCommande(long id) {
        pizzaCommandeRepository.deleteById(id);
    }

    @Override
    public List<PizzaCommandeDto> getPizzaCommandeByCommandeId(long commandeId) {
        List<PizzaCommandeDto> pizzaCommandeDtos = this.getAllPizzaCommandes();

        for (PizzaCommandeDto pizzaCommande : pizzaCommandeDtos) {
            if (pizzaCommande.getCommandeId() != commandeId) {
                pizzaCommandeDtos.remove(pizzaCommande);
            }
        }

        return pizzaCommandeDtos;
    }
}