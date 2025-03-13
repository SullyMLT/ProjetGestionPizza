package com.example.demo.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class CommentaireDto {
    private Long id;
    private String description;
    private Date date;
    private int pizzaOrigine;
    private int note;

}