package com.example.demo.services.impl;

import com.example.demo.dtos.CompteDto;
import com.example.demo.entities.Compte;
import com.example.demo.mappers.CompteMapperImpl;
import com.example.demo.repositories.CompteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompteServiceImplTest {

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private CompteMapperImpl compteMapperImpl;

    @InjectMocks
    private CompteServiceImpl compteService;

    private Compte compte;
    private CompteDto compteDto;

    @BeforeEach
    void setUp() {
        compte = new Compte();
        compte.setId(1L);
        compte.setUsername("testuser");
        compte.setPassword("password");
        compte.setRole("client");
        compte.setActiver(true);

        compteDto = new CompteDto();
        compteDto.setId(1L);
        compteDto.setUsername("testuser");
        compteDto.setPassword("password");
        compteDto.setRole("client");
        compteDto.setActiver(true);
    }

    @Test
    void createCompte() {
        when(compteMapperImpl.toEntity(any(CompteDto.class))).thenReturn(compte);
        when(compteRepository.save(any(Compte.class))).thenReturn(compte);
        when(compteMapperImpl.toDto(any(Compte.class))).thenReturn(compteDto);

        CompteDto result = compteService.createCompte(compteDto);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(compteRepository, times(1)).save(any(Compte.class));
    }

    @Test
    void updateCompte() {
        when(compteRepository.findById(1)).thenReturn(Optional.of(compte));
        when(compteRepository.save(any(Compte.class))).thenReturn(compte);
        when(compteMapperImpl.toDto(any(Compte.class))).thenReturn(compteDto);

        CompteDto result = compteService.updateCompte(1L, compteDto);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void enableDisableCompte() {
        when(compteRepository.findById(1)).thenReturn(Optional.of(compte));
        when(compteRepository.save(any(Compte.class))).thenReturn(compte);
        when(compteMapperImpl.toDto(any(Compte.class))).thenReturn(compteDto);

        CompteDto result = compteService.enableDisableCompte(1L);

        assertNotNull(result);
        verify(compteRepository, times(1)).save(any(Compte.class));
    }

    @Test
    void deleteCompte() {
        doNothing().when(compteRepository).deleteById(1);
        compteService.deleteCompte(1L);
        verify(compteRepository, times(1)).deleteById(1);
    }

    @Test
    void getCompteById() {
        when(compteRepository.findById(1)).thenReturn(Optional.of(compte));
        when(compteMapperImpl.toDto(compte)).thenReturn(compteDto);

        CompteDto result = compteService.getCompteById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getAllComptes() {
        when(compteRepository.findAll()).thenReturn(Arrays.asList(compte));
        when(compteMapperImpl.toDto(any(Compte.class))).thenReturn(compteDto);

        List<CompteDto> result = compteService.getAllComptes();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void connexion() {
        when(compteRepository.findAll()).thenReturn(List.of(compte));
        when(compteMapperImpl.toDto(compte)).thenReturn(compteDto);

        CompteDto result = compteService.connexion("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void getUsernameByCompteId() {
        when(compteRepository.findById(1)).thenReturn(Optional.of(compte));
        String result = compteService.getUsernameByCompteId(1L);

        assertNotNull(result);
        assertEquals("testuser", result);
    }
}