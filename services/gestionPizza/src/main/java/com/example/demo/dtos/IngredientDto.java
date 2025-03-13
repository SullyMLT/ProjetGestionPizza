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

}