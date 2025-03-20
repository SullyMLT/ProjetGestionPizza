package com.example.demo.mappers;

import com.example.demo.dtos.CommentaireDto;
import com.example.demo.entities.Commentaire;
import org.springframework.stereotype.Component;

@Component
public class CommentaireMapperImpl {

    public CommentaireDto toDto(Commentaire commentaire) {
        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setId(commentaire.getId());
        commentaireDto.setDescription(commentaire.getDescription());
        commentaireDto.setDate(commentaire.getDate());
        commentaireDto.setPizzaOrigine(commentaire.getPizzaOrigine());
        commentaireDto.setNote(commentaire.getNote());
        commentaireDto.setCompteId(commentaire.getCompteId());

        return commentaireDto;
    }

    public Commentaire toEntity(CommentaireDto commentaireDto) {
        Commentaire commentaire = new Commentaire();
        commentaire.setId(commentaireDto.getId());
        commentaire.setDescription(commentaireDto.getDescription());
        commentaire.setDate(commentaireDto.getDate());
        commentaire.setPizzaOrigine(commentaireDto.getPizzaOrigine());
        commentaire.setNote(commentaireDto.getNote());
        commentaire.setCompteId(commentaireDto.getCompteId());

        return commentaire;
    }
}