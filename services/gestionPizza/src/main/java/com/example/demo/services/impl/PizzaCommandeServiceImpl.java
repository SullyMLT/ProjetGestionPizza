package com.example.demo.services.impl;

import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.*;
import com.example.demo.mappers.impl.PizzaCommandeMapperImpl;
import com.example.demo.repositories.*;
import com.example.demo.services.PizzaCommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PizzaCommandeServiceImpl implements PizzaCommandeService {

    @Autowired
    private PizzaCommandeRepository pizzaCommandeRepository;
    @Autowired
    private PizzaCommandeMapperImpl pizzaCommandeMapperImpl;
    @Autowired
    private CommandeRepository commandeRepository;

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
        Long pizzaComId = pizzaCommande.getCommandeId();
        Optional<Commande> optionalCommande = commandeRepository.findById(Math.toIntExact(pizzaComId));
        if (optionalCommande.isEmpty()) {
            return null;
        }
        Commande commande = optionalCommande.get();
        commande.setPrix(commande.getPrix() + pizza.getPrix());
        commandeRepository.save(commande);
        PizzaCommandeDto pizzaComDto = pizzaCommandeMapperImpl.toDto(savedPizzaCommande);
        return pizzaComDto;
    }

    @Override
    public PizzaCommandeDto getPizzaCommandeById(Long id) {
        Optional<PizzaCommande> pizzaCommande = pizzaCommandeRepository.findById(id);
        if (pizzaCommande.isEmpty()) {
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
    public boolean deletePizzaCommande(Long id) {
        PizzaCommandeDto pizzaComDto = this.getPizzaCommandeById(id);
        pizzaComDto.setPizza(null);
        pizzaComDto.setIngredients(null);
        this.pizzaCommandeRepository.save(this.pizzaCommandeMapperImpl.toEntity(pizzaComDto));
        pizzaCommandeRepository.deleteById(id);
        return pizzaCommandeRepository.findById(id).isEmpty();
    }

    @Override
    public List<PizzaCommandeDto> getPizzaCommandeByCommandeId(Long commandeId) {
        List<PizzaCommandeDto> pizzaCommandeDtos = this.getAllPizzaCommandes();

        for (PizzaCommandeDto pizzaCommande : pizzaCommandeDtos) {
            if (!Objects.equals(pizzaCommande.getCommandeId(), commandeId)) {
                pizzaCommandeDtos.remove(pizzaCommande);
            }
        }

        return pizzaCommandeDtos;
    }
}