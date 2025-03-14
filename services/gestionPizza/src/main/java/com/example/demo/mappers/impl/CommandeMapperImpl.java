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
        if (commande.getPizzasPersonnalisees() != null) {
            for (PizzaCommande pizzaCommande : commande.getPizzasPersonnalisees()) {

                PizzaCommandeDto pizzaCommandeDto = new PizzaCommandeDto();
                pizzaCommandeDto.setId(pizzaCommande.getId());
                PizzaDto pizDto = new PizzaDto();
                pizDto.setId(pizzaCommande.getPizza().getId());
                pizDto.setNom(pizzaCommande.getPizza().getNom());
                pizDto.setDescription(pizzaCommande.getPizza().getDescription());
                pizDto.setPhoto(pizzaCommande.getPizza().getPhoto());
                pizDto.setPrix(pizzaCommande.getPizza().getPrix());
                pizzaCommandeDto.setPizzaDto(pizDto);
                List<IngredientDto> ingreDto = new ArrayList<>();
                for (Ingredient ingredient : pizzaCommande.getIngredients()) {
                    IngredientDto ingredientDto = new IngredientMapperImpl().toDto(ingredient);
                    ingreDto.add(ingredientDto);
                }
                pizzaCommandeDto.setIngredients(ingreDto);
            }
        }
        commandeDto.setPizzasPersonnalisees(pizCom);
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
        if (commandeDto.getPizzasPersonnalisees() != null) {
            for (PizzaCommandeDto pizzaCommandeDto : commandeDto.getPizzasPersonnalisees()) {
                PizzaCommande pizzaCommande = new PizzaCommandeMapperImpl().toEntity(pizzaCommandeDto);
                pizCom.add(pizzaCommande);
            }
        }
        commande.setPizzasPersonnalisees(pizCom);
        return new Commande();
    }
}