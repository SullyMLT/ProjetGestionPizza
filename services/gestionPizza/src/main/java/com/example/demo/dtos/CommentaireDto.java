package com.example.demo.dtos;

import com.example.demo.entities.Commentaire;
import lombok.Data;

import java.util.Date;

@Data
public class CommentaireDto {
    private Long id;
    private String description;
    private Date date;
    private int pizzaOrigine;
    private int note;

    public CommentaireDto() {

    }

    public CommentaireDto(long id, String description, String date, long pizzaOrigine, int note) {
        this.id = id;
        this.description = description;
        this.date = new Date(date);
        this.pizzaOrigine = (int) pizzaOrigine;
        this.note = note;
    }

    public Commentaire toEntity() {
        return new Commentaire(this.id, this.description, this.date, this.pizzaOrigine, this.note);
    }
}