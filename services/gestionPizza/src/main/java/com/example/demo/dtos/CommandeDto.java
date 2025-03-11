package com.example.demo.dtos;

import com.example.demo.entities.Commande;
import lombok.Data;

@Data
public class CommandeDto {
    private int numero;
    private String description;
    private int validation;
    private int pizzaOrigine;
    private String date;

    public CommandeDto() {
    }

    public CommandeDto(int numero, String description, int validation, int pizzaOrigine, String date) {
        this.numero = numero;
        this.description = description;
        this.validation = validation;
        this.pizzaOrigine = pizzaOrigine;
        this.date = date;
    }

    public CommandeDto CommandeToDto(Commande commande) {
        CommandeDto dto = new CommandeDto();
        dto.setNumero(commande.getNumero());
        dto.setDescription(commande.getDescription());
        dto.setValidation(commande.getValidation());
        dto.setPizzaOrigine(commande.getPizza_origine());
        dto.setDate(commande.getDate());
        return dto;
    }

    public Commande toEntity(CommandeDto dto) {
        Commande commande = new Commande();
        commande.setNumero(dto.getNumero());
        commande.setDescription(dto.getDescription());
        commande.setValidation(dto.getValidation());
        commande.setPizza_origine(dto.getPizzaOrigine());
        commande.setDate(dto.getDate());
        return commande;
    }

    public Commande toEntity() {
        Commande commande = new Commande();
        commande.setNumero(this.getNumero());
        commande.setDescription(this.getDescription());
        commande.setValidation(this.getValidation());
        commande.setPizza_origine(this.getPizzaOrigine());
        commande.setDate(this.getDate());
        return commande;
    }
}
