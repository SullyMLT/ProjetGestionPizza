package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "Pizza")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nom;
    private String description;
    private String photo;
    private float prix;

    @ManyToMany(mappedBy = "pizzas")
    private List<Commande> commandes;


    @ManyToMany
    @JoinTable(
            name = "pizza_standard",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "standard_id")
    )
    private List<Standard> standards;
}
