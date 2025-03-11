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

    public Commande toEntity() {
        return new Commande(this.numero, this.description, this.validation, this.pizzaOrigine, this.date);
    }
}
