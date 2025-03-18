package com.example.demo.dtos;

import lombok.Data;
import java.util.List;

@Data
public class PizzaCommandeDto {
    private Long id;
    private Long commandeId;
    private PizzaDto pizza;
    private List<IngredientDto> ingredients;
}