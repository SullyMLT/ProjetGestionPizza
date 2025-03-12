package com.example.demo.dtos;

import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Optionnel;
import com.example.demo.entities.Pizza;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OptionnelDto {
    private long id;
    private String nom;
    private List<Long> ingredientsId;
    private long pizzaId;

    public OptionnelDto(Optionnel optionnel) {
        this.id = optionnel.getId();
        this.nom = optionnel.getName();
        this.ingredientsId = new ArrayList<>();
        for (Ingredient ingredient : optionnel.getIngredients()) {
            this.ingredientsId.add(ingredient.getId());
        }
        this.pizzaId = optionnel.getPizza().getId();
    }

    public Optionnel toEntity(List<Ingredient> ingredients, Pizza pizza) {
        Optionnel optionnel = new Optionnel();
        optionnel.setId(this.id);
        optionnel.setName(this.nom);
        optionnel.setIngredients(ingredients);
        optionnel.setPizza(pizza);
        return optionnel;
    }
}
