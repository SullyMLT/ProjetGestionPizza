package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int numero;
    private String description;
    private int validation;
    private int pizza_origine;
    private String date;

    @ManyToMany
    @JoinTable(
            name = "commande_pizza",
            joinColumns = @JoinColumn(name = "commande_id"),
            inverseJoinColumns = @JoinColumn(name = "pizza_id")
    )
    private List<Pizza> pizzas;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<PizzaCommande> pizzasPersonnalisees;

    public Commande(){

    }

    public Commande(int numero, String description, int validation, int pizza_origine, String date) {
        this.numero = numero;
        this.description = description;
        this.validation = validation;
        this.pizza_origine = pizza_origine;
        this.date = date;
    }
}
