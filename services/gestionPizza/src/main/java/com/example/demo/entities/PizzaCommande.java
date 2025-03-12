package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "PizzaCommande")
public class PizzaCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "optionnel_id")
    private Optionnel optionnel;
}
