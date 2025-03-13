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


    public PizzaCommandeDto(PizzaCommande pizzaCommande) {
        this.setId(pizzaCommande.getId());
        this.setCommandeId(pizzaCommande.getCommande().getId());
        this.pizzaDto = new PizzaDto(pizzaCommande.getPizza());
        for (Ingredient ingredient : pizzaCommande.getIngredients()) {
            this.ingredients.add(new IngredientDto(ingredient));
        }
    }

    public PizzaCommande toEntity( Commande commande) {
        PizzaCommande pizzaCommande = new PizzaCommande();
        pizzaCommande.setId(this.getId());
        pizzaCommande.setCommande(commande);

        return pizzaCommande;
    }
}