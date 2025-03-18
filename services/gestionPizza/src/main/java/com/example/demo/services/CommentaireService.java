package com.example.demo.services;

import java.util.List;
import com.example.demo.dtos.CommentaireDto;

public interface CommentaireService {
    void addCommentaire(CommentaireDto commentaireDto);
    void deleteCommentaire(Long id);
    CommentaireDto getCommentaire(Long id);
    List<CommentaireDto> getCommentaires();
}
