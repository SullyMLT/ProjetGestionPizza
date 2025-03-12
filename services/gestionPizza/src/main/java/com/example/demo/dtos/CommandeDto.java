package com.example.demo.dtos;

import com.example.demo.entities.Commande;
import com.example.demo.entities.Pizza;
import com.example.demo.entities.PizzaCommande;
import lombok.Data;

import java.util.List;

@Data
public class CommandeDto {
    private long id;
    private int numero;
    private String description;
    private int validation;
    private int pizzaOrigine;
    private String date;
    private List<Pizza> pizzas;
    private List<PizzaCommande> pizzasPersonnalisees;

    public CommandeDto() {
    }

    public CommandeDto commandeToDto(Commande commande) {
        CommandeDto dto = new CommandeDto();
        dto.setId(commande.getId());
        dto.setNumero(commande.getNumero());
        dto.setDescription(commande.getDescription());
        dto.setValidation(commande.getValidation());
        dto.setPizzaOrigine(commande.getPizza_origine());
        dto.setDate(commande.getDate());
        for (Pizza pizza : commande.getPizzas()) {
            dto.pizzas.add(pizza);
        }
        for (PizzaCommande pizzaCommande : commande.getPizzasPersonnalisees()) {
            dto.pizzasPersonnalisees.add(pizzaCommande);
        }

        return dto;
    }

    public Commande toEntity() {
        Commande commande = new Commande();
        commande.setId(this.id);
        commande.setNumero(this.getNumero());
        commande.setDescription(this.getDescription());
        commande.setValidation(this.getValidation());
        commande.setPizza_origine(this.getPizzaOrigine());
        commande.setDate(this.getDate());
        for (Pizza pizza : this.getPizzas()) {
            commande.getPizzas().add(pizza);
        }
        for (PizzaCommande pizzaCommande : this.getPizzasPersonnalisees()) {
            commande.getPizzasPersonnalisees().add(pizzaCommande);
        }

        return commande;
    }


}
