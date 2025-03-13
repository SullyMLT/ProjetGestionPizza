package com.example.demo.dtos;

import com.example.demo.entities.Standard;
import com.example.demo.entities.Pizza;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PizzaDto {
    private long id;
    private String nom;
    private String description;
    private String photo;
    private float prix;
    private List<StandardDto> standards;

}
