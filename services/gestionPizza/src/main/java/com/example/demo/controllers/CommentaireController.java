package com.example.demo.controllers;

import com.example.demo.dtos.CommentaireDto;
import com.example.demo.repositories.CommentaireRepository;
import com.example.demo.services.impl.CommentaireServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commentaires")
public class CommentaireController {

    private CommentaireRepository commentaireRepository;
    private CommentaireServiceImpl commentaireServiceImpl = new CommentaireServiceImpl(commentaireRepository);

    @GetMapping
    public ResponseEntity<List<CommentaireDto>> getAllCommentaires() {
        return new ResponseEntity<>(commentaireServiceImpl.getCommentaires(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentaireDto> getCommentaireById(@PathVariable Long id) {
        return new ResponseEntity<>(commentaireServiceImpl.getCommentaire(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentaireDto> createCommentaire(@RequestBody CommentaireDto commentaireDto) {
        commentaireServiceImpl.addCommentaire(commentaireDto);
        return ResponseEntity.ok(commentaireDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentaireDto> updateCommentaire(@RequestBody CommentaireDto updatedCommentaire) {
        commentaireServiceImpl.updateCommentaire(updatedCommentaire);
        return ResponseEntity.ok(updatedCommentaire);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentaire(@PathVariable Long id) {
        commentaireServiceImpl.deleteCommentaire(id);
        return ResponseEntity.noContent().build();
    }
}