package com.example.demo.dtos;

import com.example.demo.entities.Ingredient;
import lombok.Data;

@Data
public class IngredientDto {
    private long id;
    private String name;
    private String description;
    private String pathPhoto;
    private double prix;

    public IngredientDto (Ingredient ingredient) {
        this.setId(ingredient.getId());
        this.setName(ingredient.getName());
        this.setDescription(ingredient.getDescription());
        this.setPathPhoto(ingredient.getPathPhoto());
        this.setPrix(ingredient.getPrix());
    }

    public Ingredient toEntity() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(this.getId());
        ingredient.setName(this.getName());
        ingredient.setDescription(this.getDescription());
        ingredient.setPathPhoto(this.getPathPhoto());
        ingredient.setPrix(this.getPrix());
        return ingredient;
    }
}