package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
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

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<PizzaCommande> pizzasPersonnalisees;
}
