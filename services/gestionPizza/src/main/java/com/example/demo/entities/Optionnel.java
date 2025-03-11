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

    @OneToMany(mappedBy = "optionnel")
    private List<Pizza> pizzas;

    @OneToMany(mappedBy = "optionnel")
    private List<Ingredient> ingredients;
}
