package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private int validation;
    private String date;
    private float prix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_id", nullable = true)
    private Compte compte;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PizzaCommande> pizzasCommandes;
}
