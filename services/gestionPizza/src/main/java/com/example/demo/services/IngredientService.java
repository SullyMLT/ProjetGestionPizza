package com.example.demo.services;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.entities.Ingredient;

import java.util.List;

public interface IngredientService {
    IngredientDto addIngredient(Ingredient ingredient);
    void deleteIngredient(long id);
    IngredientDto updateIngredient(long id, Ingredient ingredient);
    IngredientDto getIngredientById(long id);
    List<IngredientDto> getAllIngredients();
}