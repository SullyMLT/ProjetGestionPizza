package com.example.demo.services.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.*;
import com.example.demo.mappers.PizzaCommandeMapperImpl;
import com.example.demo.mappers.PizzaMapperImpl;
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
    private PizzaRepository pizzaRepository;
    @Autowired
    private PizzaMapperImpl pizzaMapperImpl;
    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public PizzaCommandeDto createPizzaCommande(PizzaCommandeDto pizzaCommandeDto) {
        PizzaCommande pizzaCommande = pizzaCommandeMapperImpl.toEntity(pizzaCommandeDto);
        Pizza pizza = pizzaCommande.getPizza();
        Optional<Commande> com = commandeRepository.findById(Math.toIntExact(pizzaCommande.getCommandeId()));
        if (com.isEmpty()) {
            return null;
        }
        Commande commande = com.get();
        if (commande.isValidation()){
            return null;
        }
        List<PizzaCommande> pizzaCommandes = this.pizzaCommandeRepository.findAll();
        List<PizzaCommande> pizzaCommandes2 = new ArrayList<>();
        if (!pizzaCommandes.isEmpty()){
            for (PizzaCommande pizzaCom : pizzaCommandes){
                if (Objects.equals(pizzaCom.getCommandeId(), commande.getId())){
                    pizzaCommandes2.add(pizzaCom);
                }
            }
        }
        float prixCom = 0;
        if (!pizzaCommandes2.isEmpty()){
            for (PizzaCommande pizzaCommandeBefore : pizzaCommandes2){
                prixCom += pizzaCommandeBefore.getPizza().getPrix();
            }
        }
        float prix = 0;
        if (!pizzaCommande.getIngredients().isEmpty()) {
            for (Ingredient ingre : pizzaCommande.getIngredients()) {
                prix += ingre.getPrix();
            }
            pizza.setPrix(prix);
        }else{
            pizza.setPrix(5f);
        }
        pizzaCommande.setPizza(pizza);
        PizzaCommande savedPizzaCommande = pizzaCommandeRepository.save(pizzaCommande);

        commande.setPrix(prixCom + prix);
        commandeRepository.save(commande);
        return pizzaCommandeMapperImpl.toDto(savedPizzaCommande);
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
        Long commandeId = pizzaComDto.getCommandeId();
        Optional<Commande> optionalCommande = commandeRepository.findById(Math.toIntExact(commandeId));
        if (optionalCommande.isEmpty()) {
            return false;
        }
        Commande commande = optionalCommande.get();
        if (commande.isValidation()){
            return false;
        }
        commande.setPrix(commande.getPrix() - pizzaComDto.getPizza().getPrix());
        Pizza pizzaDel = new Pizza();
        List<IngredientDto> ingreDel = new ArrayList<>();
        pizzaDel = pizzaRepository.save(pizzaDel);
        PizzaDto pizzaDelDto = this.pizzaMapperImpl.toDto(pizzaDel);
        pizzaComDto.setPizza(pizzaDelDto);
        pizzaComDto.setIngredients(ingreDel);
        this.pizzaCommandeRepository.save(this.pizzaCommandeMapperImpl.toEntity(pizzaComDto));
        pizzaCommandeRepository.deleteById(id);
        this.commandeRepository.save(commande);
        pizzaRepository.delete(pizzaDel);
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
        if (pizzaCommandeDtos.isEmpty()) {
            return null;
        }

        return pizzaCommandeDtos;
    }
}