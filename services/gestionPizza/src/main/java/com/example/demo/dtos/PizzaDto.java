package com.example.demo.dtos;

import java.util.List;
import lombok.Data;

@Data
public class PizzaDto {

    private Long id;
    private String nom;
    private String description;
    private String photo;
    private float prix;
    private boolean activer;
}
