package com.example.demo.services.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.PizzaCommande;
import com.example.demo.mappers.IngredientMapperImpl;
import com.example.demo.repositories.IngredientRepository;
import com.example.demo.repositories.PizzaCommandeRepository;
import com.example.demo.repositories.StandardRepository;
import com.example.demo.services.impl.IngredientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientMapperImpl ingredientMapperImpl;

    @Mock
    private StandardRepository standardRepository;

    @Mock
    private PizzaCommandeRepository pizzaCommandeRepository;

    @InjectMocks
    private IngredientServiceImpl ingredientServiceImpl;

    private Ingredient ingredient;
    private IngredientDto ingredientDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Tomato");
        ingredient.setDescription("Fresh tomato");
        ingredient.setPhoto("tomato.jpg");
        ingredient.setPrix(2.5f);

        ingredientDto = new IngredientDto();
        ingredientDto.setId(1L);
        ingredientDto.setName("Tomato");
        ingredientDto.setDescription("Fresh tomato");
        ingredientDto.setPhoto("tomato.jpg");
        ingredientDto.setPrix(2.5f);
    }

    @Test
    void addIngredient() {
        when(ingredientMapperImpl.toEntity(ingredientDto)).thenReturn(ingredient);
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        when(ingredientMapperImpl.toDto(ingredient)).thenReturn(ingredientDto);

        IngredientDto result = ingredientServiceImpl.addIngredient(ingredientDto);

        assertNotNull(result);
        assertEquals("Tomato", result.getName());
        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    void deleteIngredient() {
        when(ingredientRepository.findById(1)).thenReturn(Optional.of(ingredient));
        when(pizzaCommandeRepository.findAll()).thenReturn(Arrays.asList());
        when(standardRepository.findAll()).thenReturn(Arrays.asList());

        String result = ingredientServiceImpl.deleteIngredient(1L);

        assertEquals("Ingredient supprimé", result);
        verify(ingredientRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteIngredientCannotBeDelete() {
        when(ingredientRepository.findById(1)).thenReturn(Optional.of(ingredient));
        PizzaCommande pizzaCommande = new PizzaCommande();
        pizzaCommande.setIngredients(Arrays.asList(ingredient));
        when(pizzaCommandeRepository.findAll()).thenReturn(Arrays.asList(pizzaCommande));

        String result = ingredientServiceImpl.deleteIngredient(1L);

        assertEquals("L'ingredient ne peut pas être supprimé", result);
        verify(ingredientRepository, never()).deleteById(any());
    }

    @Test
    void updateIngredient() {
        Ingredient updatedIngredient = new Ingredient();
        updatedIngredient.setId(1L);
        updatedIngredient.setName("Updated Tomato");
        updatedIngredient.setDescription("Updated description");
        updatedIngredient.setPhoto("updated_tomato.jpg");
        updatedIngredient.setPrix(3.0f);

        when(ingredientRepository.findById(1)).thenReturn(Optional.of(ingredient));
        when(ingredientMapperImpl.toEntity(ingredientDto)).thenReturn(updatedIngredient);
        when(ingredientRepository.save(updatedIngredient)).thenReturn(updatedIngredient);
        when(ingredientMapperImpl.toDto(updatedIngredient)).thenReturn(ingredientDto);

        IngredientDto result = ingredientServiceImpl.updateIngredient(1L, ingredientDto);

        assertNotNull(result);
        assertEquals("Updated Tomato", result.getName());
        assertEquals(3.0f, result.getPrix());
        verify(ingredientRepository, times(1)).save(updatedIngredient);
    }

    @Test
    void getIngredientById() {
        when(ingredientRepository.findById(1)).thenReturn(Optional.of(ingredient));
        when(ingredientMapperImpl.toDto(ingredient)).thenReturn(ingredientDto);

        IngredientDto result = ingredientServiceImpl.getIngredientById(1L);

        assertNotNull(result);
        assertEquals("Tomato", result.getName());
    }

    @Test
    void getIngredientByIdNotFound() {
        when(ingredientRepository.findById(1)).thenReturn(Optional.empty());

        IngredientDto result = ingredientServiceImpl.getIngredientById(1L);

        assertNull(result);
    }

    @Test
    void getAllIngredients() {
        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient));
        when(ingredientMapperImpl.toDto(ingredient)).thenReturn(ingredientDto);

        var result = ingredientServiceImpl.getAllIngredients();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Tomato", result.get(0).getName());
    }
}