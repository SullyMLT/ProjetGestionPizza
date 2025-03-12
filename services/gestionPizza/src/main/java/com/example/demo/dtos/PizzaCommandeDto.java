package com.example.demo.dtos;

import com.example.demo.entities.Commande;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Optionnel;
import com.example.demo.entities.PizzaCommande;
import lombok.Data;
import java.util.List;

@Data
public class PizzaCommandeDto {
    private long id;
    private long commandeId;
    private OptionnelDto optionnelDto;

    public PizzaCommandeDto(PizzaCommande pizzaCommande) {
        this.setId(pizzaCommande.getId());
        this.setCommandeId(pizzaCommande.getCommande().getId());
        this.setOptionnelDto(new OptionnelDto(pizzaCommande.getOptionnel()));
    }

    public PizzaCommande toEntity(Optionnel optionnel, Commande commande) {
        PizzaCommande pizzaCommande = new PizzaCommande();
        pizzaCommande.setId(this.getId());
        pizzaCommande.setCommande(commande);
        pizzaCommande.setOptionnel(optionnel);

        return pizzaCommande;
    }
}