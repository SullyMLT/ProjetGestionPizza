package com.example.demo.dtos;

import com.example.demo.entities.Standard;
import lombok.Data;

@Data
public class StandardDto {
    private long id;
    private String nom;

    public StandardDto(Standard standard) {
        this.id = standard.getId();
        this.nom = standard.getName();
    }
}
