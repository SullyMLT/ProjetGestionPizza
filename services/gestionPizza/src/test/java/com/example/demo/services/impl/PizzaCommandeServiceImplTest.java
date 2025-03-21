package com.example.demo.services.impl;

import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.PizzaCommande;
import com.example.demo.entities.Commande;
import com.example.demo.mappers.PizzaCommandeMapperImpl;
import com.example.demo.repositories.PizzaCommandeRepository;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.repositories.PizzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class PizzaCommandeServiceImplTest {

    @InjectMocks
    private PizzaCommandeServiceImpl pizzaCommandeService;
    @Mock
    private PizzaCommandeRepository pizzaCommandeRepository;
    @Mock
    private PizzaCommandeMapperImpl pizzaCommandeMapperImpl;
    @Mock
    private CommandeRepository commandeRepository;
    @Mock
    private PizzaRepository pizzaRepository;

    private PizzaCommande pizzaCommande;
    private PizzaCommandeDto pizzaCommandeDto;
    private Commande commande;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pizzaCommande = new PizzaCommande();
        pizzaCommande.setId(1L);
        pizzaCommande.setCommandeId(1L);

        pizzaCommandeDto = new PizzaCommandeDto();
        pizzaCommandeDto.setId(1L);
        pizzaCommandeDto.setCommandeId(1L);

        commande = new Commande();
        commande.setId(1L);
        commande.setValidation(false);
    }

    @Test
    void createPizzaCommande() {
        Mockito.when(commandeRepository.findById(1)).thenReturn(Optional.of(commande));
        Mockito.when(pizzaCommandeRepository.save(Mockito.any(PizzaCommande.class))).thenReturn(pizzaCommande);
        Mockito.when(pizzaCommandeMapperImpl.toDto(Mockito.any(PizzaCommande.class))).thenReturn(pizzaCommandeDto);
        PizzaCommandeDto result = pizzaCommandeService.createPizzaCommande(pizzaCommandeDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getCommandeId());
    }

    @Test
    void createPizzaCommandeCommandeNotFound() {
        Mockito.when(commandeRepository.findById(1)).thenReturn(Optional.empty());
        PizzaCommandeDto result = pizzaCommandeService.createPizzaCommande(pizzaCommandeDto);

        assertNull(result);
    }

    @Test
    void createPizzaCommandeCommandeValidated() {
        commande.setValidation(true);
        Mockito.when(commandeRepository.findById(1)).thenReturn(Optional.of(commande));
        PizzaCommandeDto result = pizzaCommandeService.createPizzaCommande(pizzaCommandeDto);

        assertNull(result);
    }

    @Test
    void getPizzaCommandeById() {
        Mockito.when(pizzaCommandeRepository.findById(1L)).thenReturn(Optional.of(pizzaCommande));
        Mockito.when(pizzaCommandeMapperImpl.toDto(Mockito.any(PizzaCommande.class))).thenReturn(pizzaCommandeDto);
        PizzaCommandeDto result = pizzaCommandeService.getPizzaCommandeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getPizzaCommandeByIdNotFound() {
        Mockito.when(pizzaCommandeRepository.findById(1L)).thenReturn(Optional.empty());
        PizzaCommandeDto result = pizzaCommandeService.getPizzaCommandeById(1L);

        assertNull(result);
    }

    @Test
    void getAllPizzaCommandes() {
        Mockito.when(pizzaCommandeRepository.findAll()).thenReturn(List.of(pizzaCommande));
        Mockito.when(pizzaCommandeMapperImpl.toDto(Mockito.any(PizzaCommande.class))).thenReturn(pizzaCommandeDto);
        List<PizzaCommandeDto> result = pizzaCommandeService.getAllPizzaCommandes();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void deletePizzaCommande() {
        Mockito.when(pizzaCommandeRepository.findById(1L)).thenReturn(Optional.of(pizzaCommande));
        Mockito.when(commandeRepository.findById(1)).thenReturn(Optional.of(commande));
        boolean result = pizzaCommandeService.deletePizzaCommande(1L);

        assertTrue(result);
        Mockito.verify(pizzaCommandeRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void deletePizzaCommandeNotFound() {
        Mockito.when(pizzaCommandeRepository.findById(1L)).thenReturn(Optional.empty());
        boolean result = pizzaCommandeService.deletePizzaCommande(1L);

        assertFalse(result);
    }


    @Test
    void getPizzaCommandeByCommandeId() {
        Mockito.when(pizzaCommandeRepository.findAll()).thenReturn(new ArrayList<>());
        List<PizzaCommandeDto> result = pizzaCommandeService.getPizzaCommandeByCommandeId(1L);
        assertNull(result);
    }
}