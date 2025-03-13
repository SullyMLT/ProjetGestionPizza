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

}
