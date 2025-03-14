package com.example.demo.dtos;

import lombok.Data;
import java.util.List;

@Data
public class CommandeDto {
    private long id;
    private String description;
    private int validation;
    private String date;
    private float prix;
    private List<PizzaCommandeDto> pizzasCommandes;

}
