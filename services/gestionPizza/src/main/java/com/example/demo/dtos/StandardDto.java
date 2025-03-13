package com.example.demo.dtos;

import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Standard;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StandardDto {
    private long id;
    private String nom;
    private List<IngredientDto> ingredients;
    private PizzaDto pizza;

    public StandardDto(Standard standard) {
        this.id = standard.getId();
        this.nom = standard.getName();
        this.ingredients = new ArrayList<>();
        for (Ingredient ingredient : standard.getIngredients()) {
            this.ingredients.add(new IngredientDto(ingredient));
        }
        this.pizza = new PizzaDto(standard.getPizza());
    }
}
