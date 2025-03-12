package com.example.demo.dtos;

import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Optionnel;
import com.example.demo.entities.Standard;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
}