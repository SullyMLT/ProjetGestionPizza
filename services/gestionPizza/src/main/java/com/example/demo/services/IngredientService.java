package com.example.demo.services;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.entities.Ingredient;

import java.util.List;

public interface IngredientService {
    IngredientDto addIngredient(IngredientDto ingredientDto);
    void deleteIngredient(long id);
    IngredientDto updateIngredient(long id, IngredientDto ingredientDto);
    IngredientDto getIngredientById(long id);
    List<IngredientDto> getAllIngredients();
}