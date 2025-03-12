package com.example.demo.dtos;

import com.example.demo.entities.Optionnel;
import com.example.demo.entities.Standard;
import com.example.demo.entities.Pizza;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PizzaDto {
    private long id;
    private String nom;
    private String description;
    private String photo;
    private float prix;
    private List<OptionnelDto> optionnels;
    private List<StandardDto> standards;

    public PizzaDto(Pizza pizza) {
        this.id = pizza.getId();
        this.nom = pizza.getNom();
        this.description = pizza.getDescription();
        this.photo = pizza.getPhoto();
        this.prix = pizza.getPrix();

        this.optionnels = new ArrayList<>();
        this.standards = new ArrayList<>();

        for(Optionnel optionnel : pizza.getOptionnels()) {
            this.optionnels.add(new OptionnelDto(optionnel));
        }
        for (Standard standard : pizza.getStandards()) {
            this.standards.add(new StandardDto(standard));
        }
    }
}
