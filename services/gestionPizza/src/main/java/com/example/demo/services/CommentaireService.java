package com.example.demo.services;

import java.util.List;
import com.example.demo.dtos.CommentaireDto;

public interface CommentaireService {
    public void addCommentaire(CommentaireDto commentaireDto);
    public void deleteCommentaire(long id);
    public CommentaireDto getCommentaire(long id);
    public List<CommentaireDto> getCommentaires();
}
