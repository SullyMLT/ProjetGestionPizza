package com.example.demo.mappers.impl;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.dtos.IngredientDto;
import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Commande;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.PizzaCommande;
import com.example.demo.mappers.CommandeMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandeMapperImpl implements CommandeMapper {
    @Override
    public CommandeDto toDto(Commande commande){
        if (commande == null) {
            return null;
        }
        CommandeDto commandeDto = new CommandeDto();
        commandeDto.setId(commande.getId());
        commandeDto.setPrix(commande.getPrix());
        commandeDto.setDescription(commande.getDescription());
        commandeDto.setDate(commande.getDate());
        List<PizzaCommandeDto> pizCom = new ArrayList<>();
        if (commande.getPizzasCommandes() != null) {
            for (PizzaCommande pizzaCommande : commande.getPizzasCommandes()) {
                PizzaCommandeDto pizzaCommandeDto = new PizzaCommandeMapperImpl().toDto(pizzaCommande);
                pizCom.add(pizzaCommandeDto);
            }
        }
        commandeDto.setPizzasCommandes(pizCom);
        return commandeDto;
    }

    @Override
    public Commande toEntity(CommandeDto commandeDto){
        if (commandeDto == null) {
            return null;
        }
        Commande commande = new Commande();
        commande.setId(commandeDto.getId());
        commande.setPrix(commandeDto.getPrix());
        commande.setDescription(commandeDto.getDescription());
        commande.setDate(commandeDto.getDate());
        List<PizzaCommande> pizCom = new ArrayList<>();
        if (commandeDto.getPizzasCommandes() != null) {
            for (PizzaCommandeDto pizzaCommandeDto : commandeDto.getPizzasCommandes()) {
                PizzaCommande pizzaCommande = new PizzaCommandeMapperImpl().toEntity(pizzaCommandeDto);
                pizCom.add(pizzaCommande);
            }
        }
        commande.setPizzasCommandes(pizCom);
        return new Commande();
    }
}