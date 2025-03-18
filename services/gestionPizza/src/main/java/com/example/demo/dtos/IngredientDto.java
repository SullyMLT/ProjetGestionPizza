package com.example.demo.dtos;

import lombok.Data;

@Data
public class IngredientDto {
    private Long id;
    private String name;
    private String description;
    private String photo;
    private float prix;
}