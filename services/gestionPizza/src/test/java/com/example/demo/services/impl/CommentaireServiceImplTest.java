package com.example.demo.services.impl;

import com.example.demo.dtos.CommentaireDto;
import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Commentaire;
import com.example.demo.entities.Pizza;
import com.example.demo.mappers.CommentaireMapperImpl;
import com.example.demo.mappers.PizzaMapperImpl;
import com.example.demo.repositories.CommentaireRepository;
import com.example.demo.repositories.PizzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentaireServiceImplTest {

    @Mock
    private CommentaireRepository commentaireRepository;

    @Mock
    private CommentaireMapperImpl commentaireMapperImpl;

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private PizzaMapperImpl pizzaMapperImpl;

    @InjectMocks
    private CommentaireServiceImpl commentaireService;

    private Commentaire commentaire;
    private CommentaireDto commentaireDto;
    private Pizza pizza;
    private PizzaDto pizzaDto;

    @BeforeEach
    void setUp() {
        commentaire = new Commentaire();
        commentaire.setId(1L);
        commentaire.setDescription("Super pizza !");
        commentaire.setDate("2025-03-21");
        commentaire.setPizzaOrigine(10L);
        commentaire.setNote(5);
        commentaire.setCompteId(100L);

        commentaireDto = new CommentaireDto();
        commentaireDto.setId(1L);
        commentaireDto.setDescription("Super pizza !");
        commentaireDto.setDate("2025-03-21");
        commentaireDto.setPizzaOrigine(10L);
        commentaireDto.setNote(5);
        commentaireDto.setCompteId(100L);

        pizza = new Pizza();
        pizza.setId(10L);
        pizza.setNom("Margherita");
        pizza.setPrix(12.5f);
        pizza.setDescription("Classic pizza");
        pizza.setPhoto("margherita.jpg");
        pizza.setActiver(true);

        pizzaDto = new PizzaDto();
        pizzaDto.setId(10L);
        pizzaDto.setNom("Margherita");
        pizzaDto.setPrix(12.5f);
        pizzaDto.setDescription("Classic pizza");
        pizzaDto.setPhoto("margherita.jpg");
        pizzaDto.setActiver(true);

    }

    @Test
    void addCommentaire() {
        when(commentaireMapperImpl.toEntity(any(CommentaireDto.class))).thenReturn(commentaire);
        when(commentaireRepository.save(any(Commentaire.class))).thenReturn(commentaire);
        when(commentaireMapperImpl.toDto(any(Commentaire.class))).thenReturn(commentaireDto);

        commentaireService.addCommentaire(commentaireDto);
        verify(commentaireRepository, times(1)).save(any(Commentaire.class));
    }

    @Test
    void deleteCommentaire() {
        doNothing().when(commentaireRepository).deleteById(1);
        commentaireService.deleteCommentaire(1L);
        verify(commentaireRepository, times(1)).deleteById(1);
    }

    @Test
    void getCommentaire() {
        when(commentaireRepository.findById(1)).thenReturn(Optional.of(commentaire));
        when(commentaireMapperImpl.toDto(commentaire)).thenReturn(commentaireDto);

        CommentaireDto result = commentaireService.getCommentaire(1L);

        assertNotNull(result);
        assertEquals(commentaireDto.getId(), result.getId());
    }

    @Test
    void getCommentaireNonExistingId() {
        when(commentaireRepository.findById(1)).thenReturn(Optional.empty());

        CommentaireDto result = commentaireService.getCommentaire(1L);

        assertNull(result);
    }

    @Test
    void getCommentaires() {
        when(commentaireRepository.findAll()).thenReturn(Arrays.asList(commentaire));
        when(commentaireMapperImpl.toDto(any(Commentaire.class))).thenReturn(commentaireDto);

        List<CommentaireDto> result = commentaireService.getCommentaires();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getCommentairesByPizzaId() {
        when(commentaireRepository.findAll()).thenReturn(List.of(commentaire));
        when(commentaireMapperImpl.toDto(any(Commentaire.class))).thenReturn(commentaireDto);

        List<CommentaireDto> result = commentaireService.getCommentairesByPizzaId(10L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getPizzaOrigine());
    }
}