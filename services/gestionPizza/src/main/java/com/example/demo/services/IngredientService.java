package com.example.demo.services;

import com.example.demo.dtos.IngredientDto;
import java.util.List;

public interface IngredientService {
    IngredientDto addIngredient(IngredientDto ingredientDto);
    String deleteIngredient(Long id);
    IngredientDto updateIngredient(Long id, IngredientDto ingredientDto);
    IngredientDto getIngredientById(Long id);
    List<IngredientDto> getAllIngredients();
}