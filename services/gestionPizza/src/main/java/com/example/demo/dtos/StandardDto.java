package com.example.demo.dtos;


import lombok.Data;
import java.util.List;

@Data
public class StandardDto {
    private long id;
    private List<IngredientDto> ingredients;
    private PizzaDto pizza;
}
