package com.example.demo.mappers;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.PizzaCommande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PizzaCommandeMapperImpl {

    @Autowired
    private PizzaMapperImpl pizzaMapperImpl;
    @Autowired
    private IngredientMapperImpl ingredientMapperImpl;

    public PizzaCommandeDto toDto(PizzaCommande pizzaCommande) {
        PizzaCommandeDto pizzaCommandeDto = new PizzaCommandeDto();
        pizzaCommandeDto.setId(pizzaCommande.getId());
        pizzaCommandeDto.setCommandeId(pizzaCommande.getCommandeId());
        if (pizzaCommande.getPizza() != null) {
            pizzaCommandeDto.setPizza(pizzaMapperImpl.toDto(pizzaCommande.getPizza()));
        }
        List<IngredientDto> ingreDto = new ArrayList<>();
        if (!pizzaCommande.getIngredients().isEmpty()) {
            for (Ingredient ingredient : pizzaCommande.getIngredients()) {
                ingreDto.add(ingredientMapperImpl.toDto(ingredient));
            }
        }
        pizzaCommandeDto.setIngredients(ingreDto);

        return pizzaCommandeDto;
    }

    public PizzaCommande toEntity(PizzaCommandeDto pizzaCommandeDto) {
        PizzaCommande pizzaCommande = new PizzaCommande();
        pizzaCommande.setId(pizzaCommandeDto.getId());
        pizzaCommande.setCommandeId(pizzaCommandeDto.getCommandeId());
        if (pizzaCommandeDto.getPizza() != null) {
            pizzaCommande.setPizza(pizzaMapperImpl.toEntity(pizzaCommandeDto.getPizza()));
        }

        List<Ingredient> ingredients = new ArrayList<>();
        if (!pizzaCommandeDto.getIngredients().isEmpty()) {
            for (IngredientDto ingredientDto : pizzaCommandeDto.getIngredients()) {
                ingredients.add(ingredientMapperImpl.toEntity(ingredientDto));
            }
        }

        pizzaCommande.setIngredients(ingredients);

        return pizzaCommande;
    }
}
