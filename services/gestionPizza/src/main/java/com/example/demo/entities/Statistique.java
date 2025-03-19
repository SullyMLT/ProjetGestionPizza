package com.example.demo.entities;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@Document(collection = "Statistique")
public class Statistique {

    @Id
    private String id;
    private Map<String, Integer> statPizza = new HashMap<>();
    private Map<String, Integer> statIngredient = new HashMap<>();
}
