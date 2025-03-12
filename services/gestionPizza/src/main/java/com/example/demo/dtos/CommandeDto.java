package com.example.demo.dtos;

import com.example.demo.entities.Commande;
import com.example.demo.entities.Pizza;
import com.example.demo.entities.PizzaCommande;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommandeDto {
    private long id;
    private int numero;
    private String description;
    private int validation;
    private int pizzaOrigine;
    private String date;
    private List<PizzaDto> pizzas;
    private List<PizzaCommande> pizzasPersonnalisees;


    public CommandeDto(Commande commande) {

        this.setId(commande.getId());
        this.setNumero(commande.getNumero());
        this.setDescription(commande.getDescription());
        this.setValidation(commande.getValidation());
        this.setPizzaOrigine(commande.getPizza_origine());
        this.setDate(commande.getDate());
        this.pizzas = new ArrayList<>();
        this.pizzasPersonnalisees = new ArrayList<>();
        for (Pizza pizza : commande.getPizzas()) {
            this.pizzas.add(new PizzaDto(pizza));
        }
        for (PizzaCommande pizzaCommande : commande.getPizzasPersonnalisees()) {
            this.pizzasPersonnalisees.add(pizzaCommande);
        }
    }
}
