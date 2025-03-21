package com.example.demo.services.impl;

import com.example.demo.mappers.PizzaCommandeMapperImpl;
import com.example.demo.repositories.PizzaCommandeRepository;
import org.junit.jupiter.api.Test;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.entities.Commande;
import com.example.demo.mappers.CommandeMapperImpl;
import com.example.demo.repositories.CommandeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommandeServiceImplTest {

    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private CommandeMapperImpl commandeMapperImpl;

    @Mock
    private PizzaCommandeRepository pizzaCommandeRepository;

    @InjectMocks
    private CommandeServiceImpl commandeService;

    private Commande commande;
    private CommandeDto commandeDto;

    @BeforeEach
    void setUp() {
        commande = new Commande();
        commande.setId(1L);
        commande.setPrix(20.0f);
        commande.setDescription("Test Commande");
        commande.setValidation(false);
        commande.setCompteId(100L);

        commandeDto = new CommandeDto();
        commandeDto.setId(1L);
        commandeDto.setPrix(20.0f);
        commandeDto.setDescription("Test Commande");
        commandeDto.setValidation(false);
        commandeDto.setCompteId(100L);
    }


    @Test
    void addCommande() {
        when(commandeMapperImpl.toEntity(any(CommandeDto.class))).thenReturn(commande);
        when(commandeRepository.findAll()).thenReturn(List.of());
        when(commandeRepository.save(any(Commande.class))).thenReturn(commande);
        when(commandeMapperImpl.toDto(any(Commande.class))).thenReturn(commandeDto);

        CommandeDto result = commandeService.addCommande(commandeDto);

        assertNotNull(result);
        assertEquals(commandeDto.getId(), result.getId());
    }

    @Test
    void getAllCommandes() {
        when(commandeRepository.findAll()).thenReturn(Arrays.asList(commande));
        when(commandeMapperImpl.toDto(any(Commande.class))).thenReturn(commandeDto);

        List<CommandeDto> result = commandeService.getAllCommandes();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getCommandeById() {
        when(commandeRepository.findById(1)).thenReturn(Optional.of(commande));
        when(commandeMapperImpl.toDto(commande)).thenReturn(commandeDto);

        CommandeDto result = commandeService.getCommandeById(1L);

        assertNotNull(result);
        assertEquals(commandeDto.getId(), result.getId());
    }

    @Test
    void getCommandeByIdNonExistingId() {
        when(commandeRepository.findById(1)).thenReturn(Optional.empty());

        CommandeDto result = commandeService.getCommandeById(1L);

        assertNull(result);
    }

    @Test
    void updateCommande() {
        when(commandeRepository.findById(1)).thenReturn(Optional.of(commande));
        when(commandeRepository.save(any(Commande.class))).thenReturn(commande);
        when(commandeMapperImpl.toDto(any(Commande.class))).thenReturn(commandeDto);

        CommandeDto result = commandeService.updateCommande(1L, 25.0f);

        assertNotNull(result);
        assertEquals(25.0f, result.getPrix());
    }

    @Test
    void validateCommande() {
        when(commandeRepository.findById(1)).thenReturn(Optional.of(commande));
        when(commandeRepository.save(any(Commande.class))).thenReturn(commande);
        when(commandeMapperImpl.toDto(any(Commande.class))).thenReturn(commandeDto);
        when(pizzaCommandeRepository.findAll()).thenReturn(new ArrayList<>());

        CommandeDto result = commandeService.validateCommande(1L);

        assertNotNull(result);
        assertTrue(result.isValidation());
    }

    @Test
    void getAllCommandeFromCompteId() {
        when(commandeRepository.findAll()).thenReturn(Arrays.asList(commande));
        when(commandeMapperImpl.toDto(any(Commande.class))).thenReturn(commandeDto);

        List<CommandeDto> result = commandeService.getAllCommandeFromCompteId(100L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }


}