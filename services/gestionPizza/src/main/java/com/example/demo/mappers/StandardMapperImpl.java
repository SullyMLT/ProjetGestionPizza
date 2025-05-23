package com.example.demo.mappers;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.dtos.PizzaDto;
import com.example.demo.dtos.StandardDto;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Pizza;
import com.example.demo.entities.Standard;
import com.example.demo.services.impl.PizzaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StandardMapperImpl {

    @Autowired
    private IngredientMapperImpl ingredientMapperImpl;

    @Autowired
    private PizzaMapperImpl pizzaMapperImpl;

    public StandardDto toDto(Standard standard) {
        StandardDto standardDto = new StandardDto();
        standardDto.setId(standard.getId());
        List<IngredientDto> ingredientDtos = new ArrayList<>();
        if (standard.getIngredients() != null) {
            for (Ingredient ingredient : standard.getIngredients()) {
                IngredientDto ingredientDto = ingredientMapperImpl.toDto(ingredient);
                ingredientDtos.add(ingredientDto);
            }
        }
        standardDto.setIngredients(ingredientDtos);

        if (standard.getPizza() != null) {
            standardDto.setPizza(pizzaMapperImpl.toDto(standard.getPizza()));
        }

        return standardDto;
    }

    public Standard toEntity(StandardDto standardDto) {
        Standard standard = new Standard();
        standard.setId(standardDto.getId());
        List<Ingredient> ingredients = new ArrayList<>();
        if (standardDto.getIngredients() != null) {
            for (IngredientDto ingredientDto : standardDto.getIngredients()) {
                Ingredient ingredient = ingredientMapperImpl.toEntity(ingredientDto);
                ingredients.add(ingredient);
            }
        }
        standard.setIngredients(ingredients);
        if (standardDto.getPizza() != null) {
            PizzaDto pizzaDto = standardDto.getPizza();
            Pizza pizza = pizzaMapperImpl.toEntity(pizzaDto);
            standard.setPizza(pizza);
        }
        return standard;
    }
}