package com.example.demo.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class CommentaireDto {
    private Long id;
    private String description;
    private String date;
    private Long pizzaOrigine;
    private int note;

}