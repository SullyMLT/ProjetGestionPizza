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

    public IngredientDto ingredientToDto(Ingredient ingredient) {
        IngredientDto dto = new IngredientDto();
        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());
        dto.setDescription(ingredient.getDescription());
        dto.setPathPhoto(ingredient.getPathPhoto());
        dto.setPrix(ingredient.getPrix());
        return dto;
    }

    public Ingredient toEntity(){
        Ingredient ingredient = new Ingredient();
        ingredient.setId(this.getId());
        ingredient.setName(this.getName());
        ingredient.setDescription(this.getDescription());
        ingredient.setPathPhoto(this.getPathPhoto());
        ingredient.setPrix(this.getPrix());
        return ingredient;
    }
}