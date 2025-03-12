package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "Optionnel")
public class Optionnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "optionnel_ingredient",
            joinColumns = @JoinColumn(name = "optionnel_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

    @ManyToMany(mappedBy = "optionnels")
    private List<Pizza> pizzas;
}