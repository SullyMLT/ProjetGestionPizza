package com.example.demo.services;

import java.util.List;

import com.example.demo.dtos.CommentaireDto;
import com.example.demo.entities.Commentaire;

public interface CommentaireService {
    public void addCommentaire(CommentaireDto commentaire);
    public void deleteCommentaire(long id);
    public CommentaireDto getCommentaire(long id);
    public List<CommentaireDto> getCommentaires();
}
