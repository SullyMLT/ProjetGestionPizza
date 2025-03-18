package com.example.demo.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class StatistiqueDto {
    private long id;
    private Map<Long, Integer> statPizza;
    private Map<Long, Integer> statIngredient;
}