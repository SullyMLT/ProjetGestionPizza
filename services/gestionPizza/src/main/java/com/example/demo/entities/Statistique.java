package com.example.demo.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "Statistique")
public class Statistique {

    private String id;
    private Map<String, Integer> statPizza = new HashMap<>();
    private Map<String, Integer> statIngredient = new HashMap<>();

    public Map<String, Integer> getStatIngredient() {
        return statIngredient;
    }

    public void setStatIngredient(Map<String, Integer> statIngredient) {
        this.statIngredient = statIngredient;
    }

    public Map<String, Integer> getStatPizza() {
        return statPizza;
    }

    public void setStatPizza(Map<String, Integer> statPizza) {
        this.statPizza = statPizza;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
