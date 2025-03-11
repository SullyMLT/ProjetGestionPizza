package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "Ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private String pathPhoto;
    private double prix;

    @ManyToOne
    @JoinColumn(name = "optionnel_id")
    private Optionnel optionnel;

    @ManyToOne
    @JoinColumn(name = "standard_id")
    private Standard standard;

    @ManyToMany(mappedBy = "ingredients")
    private List<PizzaCommande> pizzasCommande;
}
