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

    public CommentaireDto() {

    }

    public CommentaireDto CommentaireToDto(Commentaire commentaire) {
        CommentaireDto dto = new CommentaireDto();
        dto.setId(commentaire.getId());
        dto.setDescription(commentaire.getDescription());
        try {
            dto.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(commentaire.getDate()));
        } catch (ParseException e) {
            System.out.println("problème convertisseur commentaire to dto avec date");
            e.printStackTrace();
        }
        dto.setPizzaOrigine((int) commentaire.getPizza_origine());
        dto.setNote(commentaire.getNote());
        return dto;
    }

    public Commentaire toEntity() {
        Commentaire commentaire = new Commentaire();
        commentaire.setId(this.id);
        commentaire.setDescription(this.description);
        commentaire.setDate(this.date.toString());
        commentaire.setPizza_origine(this.pizzaOrigine);
        commentaire.setNote(this.note);
        return commentaire;

    }


}