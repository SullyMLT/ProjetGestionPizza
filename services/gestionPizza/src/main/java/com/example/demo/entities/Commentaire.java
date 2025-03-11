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
}
