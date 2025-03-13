package com.example.demo.services.impl;

import com.example.demo.entities.Commentaire;
import com.example.demo.dtos.CommentaireDto;
import com.example.demo.mappers.CommentaireMapper;
import com.example.demo.repositories.CommentaireRepository;
import com.example.demo.services.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentaireServiceImpl implements CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private CommentaireMapper commentaireMapper;

    @Override
    public void addCommentaire(CommentaireDto commentaireDto) {
        Commentaire commentaire = this.commentaireMapper.toEntity(commentaireDto);
        commentaireRepository.save(commentaire);
    }

    @Override
    public void deleteCommentaire(long id) {
        commentaireRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public CommentaireDto getCommentaire(long id) {
        Optional<Commentaire> optionalCom = commentaireRepository.findById(Math.toIntExact(id));
        return optionalCom.isPresent() ? this.commentaireMapper.toDto(optionalCom.get()) : null;
    }

    @Override
    public List<CommentaireDto> getCommentaires() {
        List<Commentaire> commentaires =  commentaireRepository.findAll();
        List<CommentaireDto> commentaireDtos = new ArrayList<>();

        for (Commentaire com : commentaires){
            commentaireDtos.add(this.commentaireMapper.toDto(com));
        }
        return commentaireDtos;
    }
}
