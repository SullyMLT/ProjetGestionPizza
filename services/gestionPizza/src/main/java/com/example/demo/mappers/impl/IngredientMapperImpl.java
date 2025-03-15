package com.example.demo.mappers.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.entities.Ingredient;
import com.example.demo.mappers.IngredientMapper;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public IngredientDto toDto(Ingredient ingredient) {
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(ingredient.getId());
        ingredientDto.setName(ingredient.getName());
        ingredientDto.setPrix(ingredient.getPrix());
        ingredientDto.setDescription(ingredient.getDescription());
        ingredientDto.setPhoto(ingredient.getPhoto());
        return ingredientDto;
    }
    @Override
    public Ingredient toEntity(IngredientDto ingredientDto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientDto.getId());
        ingredient.setName(ingredientDto.getName());
        ingredient.setPrix(ingredientDto.getPrix());
        ingredient.setDescription(ingredientDto.getDescription());
        ingredient.setPhoto(ingredientDto.getPhoto());
        return ingredient;
    }
}