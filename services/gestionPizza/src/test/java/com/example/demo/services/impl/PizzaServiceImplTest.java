package com.example.demo.services.impl;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Pizza;
import com.example.demo.entities.Standard;
import com.example.demo.mappers.PizzaMapperImpl;
import com.example.demo.repositories.PizzaRepository;
import com.example.demo.repositories.StandardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PizzaServiceImplTest {

    @InjectMocks
    private PizzaServiceImpl pizzaService;

    @Mock
    private PizzaRepository pizzaRepository;
    @Mock
    private PizzaMapperImpl pizzaMapperImpl;
    @Mock
    private StandardRepository standardRepository;

    private Pizza pizza;
    private PizzaDto pizzaDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pizza = new Pizza();
        pizza.setId(1L);
        pizza.setNom("Margarita");
        pizza.setDescription("Pizza tomate, mozzarella et basilic");
        pizza.setPrix(12.99f);
        pizza.setActiver(true);

        pizzaDto = new PizzaDto();
        pizzaDto.setId(1L);
        pizzaDto.setNom("Margarita");
        pizzaDto.setDescription("Pizza tomate, mozzarella et basilic");
        pizzaDto.setPrix(12.99f);
        pizzaDto.setActiver(true);
    }

    @Test
    void getAllPizzas() {
        Mockito.when(pizzaRepository.findAll()).thenReturn(List.of(pizza));
        Mockito.when(pizzaMapperImpl.toDto(Mockito.any(Pizza.class))).thenReturn(pizzaDto);

        List<PizzaDto> result = pizzaService.getAllPizzas();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Margarita", result.get(0).getNom());
    }

    @Test
    void getPizzaById() {
        Mockito.when(pizzaRepository.findById(1)).thenReturn(Optional.of(pizza));
        Mockito.when(pizzaMapperImpl.toDto(Mockito.any(Pizza.class))).thenReturn(pizzaDto);
        PizzaDto result = pizzaService.getPizzaById(1L);

        assertNotNull(result);
        assertEquals("Margarita", result.getNom());
    }

    @Test
    void getPizzaByIdNotFound() {
        Mockito.when(pizzaRepository.findById(1)).thenReturn(Optional.empty());
        PizzaDto result = pizzaService.getPizzaById(1L);
        assertNull(result);
    }

    @Test
    void addPizza() {
        Mockito.when(pizzaMapperImpl.toEntity(Mockito.any(PizzaDto.class))).thenReturn(pizza);
        Mockito.when(pizzaRepository.save(Mockito.any(Pizza.class))).thenReturn(pizza);
        Mockito.when(pizzaMapperImpl.toDto(Mockito.any(Pizza.class))).thenReturn(pizzaDto);

        PizzaDto result = pizzaService.addPizza(pizzaDto);

        assertNotNull(result);
        assertEquals("Margarita", result.getNom());
        assertTrue(result.isActiver());
        assertEquals(0f, result.getPrix());
    }

    @Test
    void updatePizza() {
        Pizza updatedPizza = new Pizza();
        updatedPizza.setId(1L);
        updatedPizza.setNom("Margarita Updated");
        updatedPizza.setDescription("Updated description");
        updatedPizza.setPrix(15.99f);
        updatedPizza.setActiver(true);

        Mockito.when(pizzaRepository.findById(1)).thenReturn(Optional.of(pizza));
        Mockito.when(pizzaMapperImpl.toEntity(Mockito.any(PizzaDto.class))).thenReturn(updatedPizza);
        Mockito.when(pizzaRepository.save(Mockito.any(Pizza.class))).thenReturn(updatedPizza);
        Mockito.when(pizzaMapperImpl.toDto(Mockito.any(Pizza.class))).thenReturn(pizzaDto);

        PizzaDto result = pizzaService.updatePizza(1L, pizzaDto);

        assertNotNull(result);
        assertEquals("Margarita Updated", result.getNom());
        assertEquals(15.99f, result.getPrix());
    }

    @Test
    void updatePizzaNotFound() {
        Mockito.when(pizzaRepository.findById(1)).thenReturn(Optional.empty());
        PizzaDto result = pizzaService.updatePizza(1L, pizzaDto);
        assertNull(result);
    }

    @Test
    void deletePizza() {
        Mockito.when(pizzaRepository.findById(1)).thenReturn(Optional.of(pizza));
        Mockito.when(standardRepository.findAll()).thenReturn(List.of());
        boolean result = pizzaService.deletePizza(1L);

        assertTrue(result);
        Mockito.verify(pizzaRepository, Mockito.times(1)).save(Mockito.any(Pizza.class));
    }

    @Test
    void deletePizzaNotFound() {
        Mockito.when(pizzaRepository.findById(1)).thenReturn(Optional.empty());
        boolean result = pizzaService.deletePizza(1L);
        assertFalse(result);
    }

}