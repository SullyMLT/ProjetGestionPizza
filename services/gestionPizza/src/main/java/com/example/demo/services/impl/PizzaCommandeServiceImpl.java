package com.example.demo.services.impl;

import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.*;
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
    private OptionnelRepository optionnelRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private CommandeServiceImpl commandeServiceImpl;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public PizzaCommandeDto createPizzaCommande(PizzaCommandeDto pizzaCommandeDto) {
        PizzaCommande pizzaCommande = new PizzaCommande();
        // Map DTO to entity

        // get la pizza pour optionnel
        Optional<Pizza> pizza = pizzaRepository.findById((int) pizzaCommandeDto.getOptionnelDto().getPizzaId());
        if (pizza.isEmpty()) {
            return null;
        }


        // get les ingredients pour Optionnel

        List<Ingredient> ingredients = new ArrayList<>();

        for (Long ingredientId : ) {
            int id = ingredientId.intValue();
            Optional<Ingredient> ingredient = ingredientRepository.findById(id);
            if (ingredient.isEmpty()) {
                return null;
            }
            ingredients.add(ingredient.get());
        }

        Optionnel optionnel = optionnelDto.toEntity(ingredients,pizza.get());

        Optional<Commande> commandeOptional = commandeRepository.findById((int) pizzaCommandeDto.getCommandeId());
        if (commandeOptional.isEmpty()) {
            return null;
        }
        Commande commande = commandeOptional.get();

        pizzaCommande = pizzaCommandeDto.toEntity(optionnel, commande);

        commande.getPizzasPersonnalisees().add(pizzaCommande);
        // Update commande créer précédemment avec les pizzas personnalisées dedans
        commandeServiceImpl.updateCommande(commande.getId(), commande);
        // Save entity
        PizzaCommande savedPizzaCommande = pizzaCommandeRepository.save(pizzaCommande);

        return new PizzaCommandeDto(savedPizzaCommande);
    }

    @Override
    public PizzaCommandeDto getPizzaCommandeById(long id) {
        Optional<PizzaCommande> pizzaCommandeOptional = pizzaCommandeRepository.findById(id);
        if (pizzaCommandeOptional.isEmpty()) {
            return null;
        }
        PizzaCommande pizzaCommande = pizzaCommandeOptional.get();
        // Map entity to DTO
        //PizzaCommandeDto pizzaCommandeDto = new PizzaCommandeDto(pizzaCommande);

        return new PizzaCommandeDto(pizzaCommande);
    }

    @Override
    public List<PizzaCommandeDto> getAllPizzaCommandes() {
        List<PizzaCommande> pizzaCommandes = pizzaCommandeRepository.findAll();
        List<PizzaCommandeDto> pizzaCommandeDtos = new ArrayList<>();
        for (PizzaCommande pizzaCommande : pizzaCommandes) {
            // Map entity to DTO
            pizzaCommandeDtos.add(new PizzaCommandeDto(pizzaCommande));
        }
        return pizzaCommandeDtos;
    }

    @Override
    public void deletePizzaCommande(long id) {
        pizzaCommandeRepository.deleteById(id);
    }
}