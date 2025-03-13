package com.example.demo.mappers;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.entities.Ingredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientDto toDto(Ingredient ingredient);
    Ingredient toEntity(IngredientDto ingredientDto);
}