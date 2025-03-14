package com.example.demo.dtos;

import lombok.Data;
import java.util.List;

@Data
public class PizzaCommandeDto {
    private long id;
    private long commandeId;
    private PizzaDto pizzaDto;
    private List<IngredientDto> ingredients;
}