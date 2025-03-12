package com.example.demo.dtos;

import com.example.demo.entities.Optionnel;
import lombok.Data;

@Data
public class OptionnelDto {
    private long id;
    private String nom;

    public OptionnelDto(Optionnel optionnel) {
        this.id = optionnel.getId();
        this.nom = optionnel.getName();
    }
}
