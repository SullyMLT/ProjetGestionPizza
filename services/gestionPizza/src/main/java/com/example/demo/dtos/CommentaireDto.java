package com.example.demo.dtos;

import com.example.demo.entities.Commentaire;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class CommentaireDto {
    private Long id;
    private String description;
    private Date date;
    private int pizzaOrigine;
    private int note;

}