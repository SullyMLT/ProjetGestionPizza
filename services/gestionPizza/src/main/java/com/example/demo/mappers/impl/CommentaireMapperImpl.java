package com.example.demo.mappers.impl;

import com.example.demo.dtos.CommentaireDto;
import com.example.demo.entities.Commentaire;
import com.example.demo.mappers.CommentaireMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentaireMapperImpl implements CommentaireMapper {
    public CommentaireDto toDto(Commentaire commentaire) {
        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setId(commentaire.getId());
        commentaireDto.setDescription(commentaire.getDescription());
        commentaireDto.setDate(commentaire.getDate());
        commentaireDto.setPizzaOrigine(commentaire.getPizzaOrigine());
        commentaireDto.setNote(commentaire.getNote());

        return commentaireDto;
    }
    public Commentaire toEntity(CommentaireDto commentaireDto) {
        Commentaire commentaire = new Commentaire();
        commentaire.setId(commentaireDto.getId());
        commentaire.setDescription(commentaireDto.getDescription());
        commentaire.setDate(commentaireDto.getDate());
        commentaire.setPizzaOrigine(commentaireDto.getPizzaOrigine());
        commentaire.setNote(commentaireDto.getNote());

        return commentaire;
    }
}