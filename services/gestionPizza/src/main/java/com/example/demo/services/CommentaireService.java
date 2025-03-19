package com.example.demo.services;

import java.util.List;
import com.example.demo.dtos.CommentaireDto;

public interface CommentaireService {
    void addCommentaire(CommentaireDto commentaireDto, Long compteId);
    void deleteCommentaire(Long id);
    CommentaireDto getCommentaire(Long id);
    List<CommentaireDto> getCommentaires();
    List<CommentaireDto> getCommentairesByPizzaId(Long pizzaId);
}
