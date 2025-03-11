package com.example.demo.entities;

import com.example.demo.dtos.CommentaireDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "Commentaire")
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private String date;
    private long pizza_origine;
    private int note;

    public Commentaire(Long id, String description, Date date, int pizzaOrigine, int note) {
        this.id = id;
        this.description = description;
        this.date = date.toString();
        this.pizza_origine = pizzaOrigine;
        this.note = note;
    }

    public CommentaireDto toDto() {
        return new CommentaireDto(this.id, this.description, this.date, this.pizza_origine, this.note);
    }

    public Commentaire(){

    }

}
