package com.example.demo.mappers.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.Commande;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.PizzaCommande;
import com.example.demo.mappers.PizzaCommandeMapper;
import com.example.demo.mappers.PizzaMapper;
import com.example.demo.mappers.IngredientMapper;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.services.impl.CommandeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PizzaCommandeMapperImpl implements PizzaCommandeMapper {

    @Autowired
    private PizzaMapper pizzaMapper;
    @Autowired
    private IngredientMapper ingredientMapper;
    @Autowired
    private CommandeServiceImpl commandeServiceImpl;

    @Override
    public PizzaCommandeDto toDto(PizzaCommande pizzaCommande) {
        PizzaCommandeDto pizzaCommandeDto = new PizzaCommandeDto();
        pizzaCommandeDto.setId(pizzaCommande.getId());
        pizzaCommandeDto.setCommandeId(pizzaCommande.getCommandeId());
        pizzaCommandeDto.setPizza(pizzaMapper.toDto(pizzaCommande.getPizza()));
        List<IngredientDto> ingreDto = new ArrayList<>();
        for (Ingredient ingredient : pizzaCommande.getIngredients()) {
            ingreDto.add(ingredientMapper.toDto(ingredient));
        }
        pizzaCommandeDto.setIngredients(ingreDto);

        return pizzaCommandeDto;
    }

    @Override
    public PizzaCommande toEntity(PizzaCommandeDto pizzaCommandeDto) {
        PizzaCommande pizzaCommande = new PizzaCommande();
        pizzaCommande.setId(pizzaCommandeDto.getId());
        pizzaCommande.setCommandeId(pizzaCommandeDto.getCommandeId());

        pizzaCommande.setPizza(pizzaMapper.toEntity(pizzaCommandeDto.getPizza()));

        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientDto ingredientDto : pizzaCommandeDto.getIngredients()) {
            ingredients.add(ingredientMapper.toEntity(ingredientDto));
        }
        pizzaCommande.setIngredients(ingredients);

        return pizzaCommande;
    }
}
