package com.example.demo.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class StatistiqueDto {
    private String id;
    private Map<String, Integer> statPizza;
    private Map<String, Integer> statIngredient;
}