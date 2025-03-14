package com.example.demo.mappers.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.Commande;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.PizzaCommande;
import com.example.demo.mappers.PizzaCommandeMapper;
import com.example.demo.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PizzaCommandeMapperImpl implements PizzaCommandeMapper {

    @Autowired
    private CommandeRepository commandeRepository;

    public PizzaCommandeDto toDto(PizzaCommande pizzaCommande){
        PizzaCommandeDto pizzaCommandeDto = new PizzaCommandeDto();
        pizzaCommandeDto.setId(pizzaCommande.getId());
        pizzaCommandeDto.setCommandeId(pizzaCommande.getCommande().getId());
        pizzaCommandeDto.setPizzaDto(new PizzaMapperImpl().toDto(pizzaCommande.getPizza()));
        List<IngredientDto> ingreDto = new ArrayList<>();
        for (Ingredient ingredient : pizzaCommande.getIngredients()) {
            IngredientDto ingredientDto = new IngredientMapperImpl().toDto(ingredient);
            ingreDto.add(ingredientDto);
        }
        pizzaCommandeDto.setIngredients(ingreDto);
        return pizzaCommandeDto;
    }
    public PizzaCommande toEntity(PizzaCommandeDto pizzaCommandeDto){
        PizzaCommande pizzaCommande = new PizzaCommande();
        pizzaCommande.setId(pizzaCommandeDto.getId());
        Commande com = commandeRepository.findById((int) pizzaCommandeDto.getCommandeId()).get();
        pizzaCommande.setCommande(com);
        pizzaCommande.setPizza(new PizzaMapperImpl().toEntity(pizzaCommandeDto.getPizzaDto()));
        List<Ingredient> ingre = new ArrayList<>();
        for (IngredientDto ingredientDto : pizzaCommandeDto.getIngredients()) {
            Ingredient ingredient = new IngredientMapperImpl().toEntity(ingredientDto);
            ingre.add(ingredient);
        }
        pizzaCommande.setIngredients(ingre);
        return pizzaCommande;
    }
}