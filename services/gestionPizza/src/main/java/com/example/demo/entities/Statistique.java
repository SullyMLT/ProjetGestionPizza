package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Table(name = "Statistique")
public class Statistique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ElementCollection
    @CollectionTable(name = "stat_pizza", joinColumns = @JoinColumn(name = "statistique_id"))
    @MapKeyColumn(name = "pizza_id")
    @Column(name = "count")
    private Map<Long, Integer> statPizza = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "stat_ingredient", joinColumns = @JoinColumn(name = "statistique_id"))
    @MapKeyColumn(name = "ingredient_id")
    @Column(name = "count")
    private Map<Long, Integer> statIngredient = new HashMap<>();

}
