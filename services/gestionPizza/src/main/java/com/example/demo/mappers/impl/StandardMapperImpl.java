package com.example.demo.mappers.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.dtos.PizzaDto;
import com.example.demo.dtos.StandardDto;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Pizza;
import com.example.demo.entities.Standard;
import com.example.demo.mappers.StandardMapper;
import com.example.demo.services.impl.IngredientServiceImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StandardMapperImpl implements StandardMapper {
    public StandardDto toDto(Standard standard) {
        StandardDto standardDto = new StandardDto();
        standardDto.setId(standard.getId());
        List<IngredientDto> ingredientDtos = new ArrayList<>();
        if (standard.getIngredients() != null) {
            for (Ingredient ingredient : standard.getIngredients()) {
                IngredientDto ingredientDto = new IngredientMapperImpl().toDto(ingredient);
                ingredientDtos.add(ingredientDto);
            }
        }
        standardDto.setIngredients(ingredientDtos);

        if (standard.getPizza() != null) {
            standardDto.setPizzaId(standard.getPizza().getId());
        }else{
            standardDto.setPizzaId(444);
        }


        return standardDto;
    }
    public Standard toEntity(StandardDto standardDto) {
        Standard standard = new Standard();
        standard.setId(standardDto.getId());
        List<Ingredient> ingredients = new ArrayList<>();
        if (standardDto.getIngredients() != null) {
            for (IngredientDto ingredientDto : standardDto.getIngredients()) {
                Ingredient ingredient = new IngredientMapperImpl().toEntity(ingredientDto);
                ingredients.add(ingredient);
            }
        }
        standard.setIngredients(ingredients);
        if (standardDto.getPizzaId() != 0) {
            long pizzaDtoId = standardDto.getPizzaId();
            PizzaDto pizzaDto = new PizzaDto();
            pizzaDto.setId(pizzaDtoId);
            Pizza pizza = new PizzaMapperImpl().toEntity(pizzaDto);
            standard.setPizza(pizza);
        }
        return standard;
    }
}