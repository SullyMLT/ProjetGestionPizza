package com.example.demo.services.impl;

import com.example.demo.entities.Commentaire;
import com.example.demo.dtos.CommentaireDto;
import com.example.demo.mappers.CommentaireMapper;
import com.example.demo.mappers.impl.CommentaireMapperImpl;
import com.example.demo.repositories.CommentaireRepository;
import com.example.demo.services.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentaireServiceImpl implements CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private CommentaireMapperImpl commentaireMapperImpl;

    @Override
    public void addCommentaire(CommentaireDto commentaireDto, Long compteId) {
        Commentaire commentaire = this.commentaireMapperImpl.toEntity(commentaireDto);
        commentaireRepository.save(commentaire);
    }

    @Override
    public void deleteCommentaire(Long id) {
        commentaireRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public CommentaireDto getCommentaire(Long id) {
        Optional<Commentaire> optionalCom = commentaireRepository.findById(Math.toIntExact(id));
        return optionalCom.isPresent() ? this.commentaireMapperImpl.toDto(optionalCom.get()) : null;
    }

    @Override
    public List<CommentaireDto> getCommentaires() {
        List<Commentaire> commentaires =  commentaireRepository.findAll();
        List<CommentaireDto> commentaireDtos = new ArrayList<>();

        for (Commentaire com : commentaires){
            commentaireDtos.add(this.commentaireMapperImpl.toDto(com));
        }
        return commentaireDtos;
    }

    @Override
    public List<CommentaireDto> getCommentairesByPizzaId(Long pizzaId) {
        List<CommentaireDto> comDto = getCommentaires();
        if (!comDto.isEmpty()){
            for (CommentaireDto com : comDto){
                if (!Objects.equals(com.getPizzaOrigine(), pizzaId)){
                    comDto.remove(com);
                }
            }
            return comDto;
        }else{
            return null;
        }
    }
}
