package com.example.demo.mappers;

import com.example.demo.dtos.CommentaireDto;
import com.example.demo.entities.Commentaire;

public interface CommentaireMapper {
    CommentaireDto toDto(Commentaire commentaire);
    Commentaire toEntity(CommentaireDto commentaireDto);
}