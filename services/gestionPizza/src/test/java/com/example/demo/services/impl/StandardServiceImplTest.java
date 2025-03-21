package com.example.demo.services.impl;

import com.example.demo.dtos.StandardDto;
import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Pizza;
import com.example.demo.entities.Standard;
import com.example.demo.mappers.PizzaMapperImpl;
import com.example.demo.mappers.StandardMapperImpl;
import com.example.demo.repositories.PizzaRepository;
import com.example.demo.repositories.StandardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StandardServiceImplTest {

    @InjectMocks
    private StandardServiceImpl standardService;

    @Mock
    private StandardRepository standardRepository;
    @Mock
    private StandardMapperImpl standardMapperImpl;
    @Mock
    private PizzaRepository pizzaRepository;
    @Mock
    private PizzaMapperImpl pizzaMapperImpl;

    private Standard standard;
    private StandardDto standardDto;
    private Pizza pizza;
    private PizzaDto pizzaDto;
    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pizza = new Pizza();
        pizza.setId(1L);
        pizza.setNom("Margherita");
        pizza.setDescription("Pizza classique");
        pizza.setPhoto("margherita.jpg");
        pizza.setPrix(0f);

        pizzaDto = new PizzaDto();
        pizzaDto.setId(1L);
        pizzaDto.setNom("Margherita");
        pizzaDto.setDescription("Pizza classique");
        pizzaDto.setPhoto("margherita.jpg");
        pizzaDto.setPrix(0f);
        pizzaDto.setActiver(true);

        ingredient = new Ingredient();
        ingredient.setId(10L);
        ingredient.setName("Tomate");
        ingredient.setDescription("Tomate fraîche");
        ingredient.setPhoto("tomate.jpg");
        ingredient.setPrix(2.0f);

        standard = new Standard();
        standard.setId(100L);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);
        standard.setIngredients(ingredients);
        standard.setPizza(pizza);

        standardDto = new StandardDto();
        standardDto.setId(100L);
        List<com.example.demo.dtos.IngredientDto> ingredientsDto = new ArrayList<>();

        com.example.demo.dtos.IngredientDto ingredientDto = new com.example.demo.dtos.IngredientDto();
        ingredientDto.setId(10L);
        ingredientDto.setName("Tomate");
        ingredientDto.setDescription("Tomate fraîche");
        ingredientDto.setPhoto("tomate.jpg");
        ingredientDto.setPrix(2.0f);
        ingredientsDto.add(ingredientDto);
        standardDto.setIngredients(ingredientsDto);
        standardDto.setPizza(pizzaDto);
    }

    @Test
    void addStandard() {
        when(standardMapperImpl.toEntity(standardDto)).thenReturn(standard);
        when(pizzaRepository.save(any(Pizza.class))).thenReturn(pizza);
        when(standardRepository.save(standard)).thenReturn(standard);
        when(standardMapperImpl.toDto(standard)).thenReturn(standardDto);
        StandardDto result = standardService.addStandard(standardDto);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertNotNull(result.getPizza());

        verify(pizzaRepository, times(1)).save(any(Pizza.class));
        verify(standardRepository, times(1)).save(any(Standard.class));
    }

    @Test
    void deleteStandard() {
        when(standardRepository.findById(100L)).thenReturn(Optional.empty());
        boolean result = standardService.deleteStandard(100L);
        assertTrue(result);
        verify(standardRepository, times(1)).deleteById(100L);
    }

    @Test
    void deleteStandardEchec() {
        when(standardRepository.findById(100L)).thenReturn(Optional.of(standard));
        boolean result = standardService.deleteStandard(100L);
        assertFalse(result);
        verify(standardRepository, times(1)).deleteById(100L);
    }

    @Test
    void updateStandard() {
        StandardDto updatedDto = new StandardDto();
        updatedDto.setId(100L);
        updatedDto.setPizza(pizzaDto);
        List<com.example.demo.dtos.IngredientDto> newIngredientsDto = new ArrayList<>();
        com.example.demo.dtos.IngredientDto newIngredientDto = new com.example.demo.dtos.IngredientDto();
        newIngredientDto.setId(20L);
        newIngredientDto.setName("Basilic");
        newIngredientDto.setDescription("Frais");
        newIngredientDto.setPhoto("basilic.jpg");
        newIngredientDto.setPrix(1.0f);
        newIngredientsDto.add(newIngredientDto);
        updatedDto.setIngredients(newIngredientsDto);

        Standard updatedStandardEntity = new Standard();
        updatedStandardEntity.setId(100L);
        List<Ingredient> newIngredients = new ArrayList<>();
        Ingredient newIngredient = new Ingredient();
        newIngredient.setId(20L);
        newIngredient.setName("Basilic");
        newIngredient.setDescription("Frais");
        newIngredient.setPhoto("basilic.jpg");
        newIngredient.setPrix(1.0f);
        newIngredients.add(newIngredient);
        updatedStandardEntity.setIngredients(newIngredients);
        updatedStandardEntity.setPizza(pizza);

        when(standardRepository.findById(100L)).thenReturn(Optional.of(standard));
        when(standardMapperImpl.toEntity(updatedDto)).thenReturn(updatedStandardEntity);
        when(pizzaRepository.save(any(Pizza.class))).thenReturn(pizza);
        when(standardRepository.save(any(Standard.class))).thenReturn(updatedStandardEntity);
        when(standardMapperImpl.toDto(updatedStandardEntity)).thenReturn(updatedDto);

        StandardDto result = standardService.updateStandard(100L, updatedDto);
        assertNotNull(result);
        verify(pizzaRepository, times(1)).save(any(Pizza.class));
        verify(standardRepository, times(1)).save(any(Standard.class));

    }

    @Test
    void updateStandard_NotFound() {
        when(standardRepository.findById(100L)).thenReturn(Optional.empty());
        StandardDto result = standardService.updateStandard(100L, standardDto);
        assertNull(result);
    }

    @Test
    void getStandardById() {
        when(standardRepository.findById(100L)).thenReturn(Optional.of(standard));
        when(standardMapperImpl.toDto(standard)).thenReturn(standardDto);

        StandardDto result = standardService.getStandardById(100L);
        assertNotNull(result);
        assertEquals(100L, result.getId());
    }

    @Test
    void getStandardByIdNotFound() {
        when(standardRepository.findById(100L)).thenReturn(Optional.empty());
        StandardDto result = standardService.getStandardById(100L);
        assertNull(result);
    }

    @Test
    void getStandardByPizzaId() {
        when(pizzaRepository.findById(1)).thenReturn(Optional.of(pizza));
        when(pizzaMapperImpl.toDto(pizza)).thenReturn(pizzaDto);
        List<StandardDto> allStandards = new ArrayList<>();
        allStandards.add(standardDto);
        when(standardRepository.findAll()).thenReturn(List.of(standard));
        when(standardMapperImpl.toDto(standard)).thenReturn(standardDto);

        StandardDto result = standardService.getStandardByPizzaId(1L);
        assertNotNull(result);
        assertEquals(standardDto.getId(), result.getId());
    }

    @Test
    void getStandardByPizzaIdNotFound() {
        when(pizzaRepository.findById(1)).thenReturn(Optional.empty());
        StandardDto result = standardService.getStandardByPizzaId(1L);
        assertNull(result);
    }

    @Test
    void getAllStandards() {
        List<Standard> standards = new ArrayList<>();
        standards.add(standard);
        when(standardRepository.findAll()).thenReturn(standards);
        when(standardMapperImpl.toDto(standard)).thenReturn(standardDto);

        List<StandardDto> result = standardService.getAllStandards();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}