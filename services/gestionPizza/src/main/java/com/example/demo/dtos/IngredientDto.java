package com.example.demo.dtos;

import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Optionnel;
import com.example.demo.entities.Standard;
import lombok.Data;

import java.util.List;

@Data
public class IngredientDto {
    private long id;
    private String name;
    private String description;
    private String pathPhoto;
    private double prix;
    private List<OptionnelDto> optionnels;
    private List<StandardDto> standards;

    public IngredientDto() {

    }

    public IngredientDto (Ingredient ingredient) {
        this.setId(ingredient.getId());
        this.setName(ingredient.getName());
        this.setDescription(ingredient.getDescription());
        this.setPathPhoto(ingredient.getPathPhoto());
        this.setPrix(ingredient.getPrix());
        for (Optionnel optionnel : ingredient.getOptionnels()) {
            this.getOptionnels().add(new OptionnelDto(optionnel));
        }
        for (Standard standard : ingredient.getStandards()) {
            this.getStandards().add(new StandardDto(standard));
        }
    }
}