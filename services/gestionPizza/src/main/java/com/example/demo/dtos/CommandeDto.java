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
    private String description;
    private int validation;
    private String date;
    private float prix;
    private List<PizzaCommandeDto> pizzasPersonnalisees;

}
