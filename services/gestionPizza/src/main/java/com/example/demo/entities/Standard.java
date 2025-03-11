package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "Standard")
public class Standard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "standard")
    private List<Pizza> pizzas;

    @OneToMany(mappedBy = "standard")
    private List<Ingredient> ingredients;
}
