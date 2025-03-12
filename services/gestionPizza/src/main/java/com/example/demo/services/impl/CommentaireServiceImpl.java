package com.example.demo.services.impl;

import com.example.demo.entities.Commentaire;
import com.example.demo.dtos.CommentaireDto;
import com.example.demo.repositories.CommentaireRepository;
import com.example.demo.services.CommentaireService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentaireServiceImpl implements CommentaireService {

    private CommentaireRepository commentaireRepository;

    public CommentaireServiceImpl(CommentaireRepository commentaireRepository) {
        this.commentaireRepository = commentaireRepository;
    }

    public void addCommentaire(CommentaireDto commentaireDto) {
        Commentaire commentaire = commentaireDto.toEntity();
        commentaireRepository.save(commentaire);
    }

    @Override
    public void deleteCommentaire(long id) {
        commentaireRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public CommentaireDto getCommentaire(long id) {
        Optional optionalCom = commentaireRepository.findById(Math.toIntExact(id));

        if (optionalCom.isPresent()) {
            Commentaire commentaire = (Commentaire) optionalCom.get();
            return new CommentaireDto().CommentaireToDto(commentaire);
        } else {
            return null;
        }
    }

    @Override
    public List<CommentaireDto> getCommentaires() {
        List<Commentaire> Commentaires =  commentaireRepository.findAll();
        List<CommentaireDto> commentaireDtos = new ArrayList<>();

        for (Commentaire comDto : Commentaires){
            commentaireDtos.add(new CommentaireDto().CommentaireToDto(comDto));
        }
        return commentaireDtos;
    }
}
