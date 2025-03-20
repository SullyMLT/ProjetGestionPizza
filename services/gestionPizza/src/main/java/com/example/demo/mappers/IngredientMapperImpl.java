package com.example.demo.mappers;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.entities.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapperImpl {

    public IngredientDto toDto(Ingredient ingredient) {
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(ingredient.getId());
        ingredientDto.setName(ingredient.getName());
        ingredientDto.setPrix(ingredient.getPrix());
        ingredientDto.setDescription(ingredient.getDescription());
        ingredientDto.setPhoto(ingredient.getPhoto());
        return ingredientDto;
    }

    public Ingredient toEntity(IngredientDto ingredientDto) {
        Ingredient ingredient = new Ingredient();
        if (ingredientDto.getId() != null){
            ingredient.setId(ingredientDto.getId());
        }
        ingredient.setId(ingredientDto.getId());
        ingredient.setName(ingredientDto.getName());
        ingredient.setPrix(ingredientDto.getPrix());
        ingredient.setDescription(ingredientDto.getDescription());
        ingredient.setPhoto(ingredientDto.getPhoto());
        return ingredient;
    }
}