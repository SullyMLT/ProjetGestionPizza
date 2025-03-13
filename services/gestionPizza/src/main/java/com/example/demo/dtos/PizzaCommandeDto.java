package com.example.demo.dtos;

import com.example.demo.entities.Commande;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.PizzaCommande;
import lombok.Data;

import java.util.List;

@Data
public class PizzaCommandeDto {
    private long id;
    private long commandeId;
    private PizzaDto pizzaDto;
    private List<IngredientDto> ingredients;

}