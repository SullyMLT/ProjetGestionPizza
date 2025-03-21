package com.example.demo.services.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.dtos.PizzaDto;
import com.example.demo.dtos.StatistiqueDto;
import com.example.demo.entities.Statistique;
import com.example.demo.mappers.StatistiqueMapperImpl;
import com.example.demo.repositories.StatistiqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatistiqueServiceImplTest {

    @InjectMocks
    private StatistiqueServiceImpl statistiqueService;

    @Mock
    private StatistiqueRepository statistiqueRepository;
    @Mock
    private StatistiqueMapperImpl statistiqueMapper;

    private Statistique statistique;
    private StatistiqueDto statistiqueDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        statistique = new Statistique();
        statistique.setId("1");
        statistique.setStatPizza(new HashMap<>());
        statistique.setStatIngredient(new HashMap<>());

        statistiqueDto = new StatistiqueDto();
        statistiqueDto.setId("1");
        statistiqueDto.setStatPizza(new HashMap<>());
        statistiqueDto.setStatIngredient(new HashMap<>());
    }

    @Test
    void getStatistique() {
        when(statistiqueRepository.findById("1")).thenReturn(Optional.of(statistique));
        when(statistiqueMapper.toDto(statistique)).thenReturn(statistiqueDto);

        StatistiqueDto result = statistiqueService.getStatistique();
        assertNotNull(result);
        assertEquals("1", result.getId());

        verify(statistiqueRepository, times(1)).findById("1");
        verify(statistiqueMapper, times(1)).toDto(statistique);
    }

    @Test
    void getStatistiqueNotFound() {
        when(statistiqueRepository.findById("1")).thenReturn(Optional.empty());
        StatistiqueDto result = statistiqueService.getStatistique();
        assertNull(result);
        verify(statistiqueRepository, times(1)).findById("1");
    }

    @Test
    void updateStatistiqueList() {
        List<PizzaCommandeDto> pizzaCommandeDtos = new ArrayList<>();

        when(statistiqueRepository.findById("1")).thenReturn(Optional.empty());

        when(statistiqueRepository.save(any(Statistique.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(statistiqueMapper.toDto(any(Statistique.class))).thenAnswer(invocation -> {
            Statistique stat = invocation.getArgument(0);
            StatistiqueDto dto = new StatistiqueDto();
            dto.setId(stat.getId());
            dto.setStatPizza(stat.getStatPizza());
            dto.setStatIngredient(stat.getStatIngredient());
            return dto;
        });

        StatistiqueDto result = statistiqueService.updateStatistiqueList(pizzaCommandeDtos);
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertTrue(result.getStatPizza().isEmpty());
        assertTrue(result.getStatIngredient().isEmpty());
    }

    @Test
    void updateStatistiqueListUpdateExistingStatistique() {
        PizzaDto pizzaDto = new PizzaDto();
        pizzaDto.setId(100L);
        pizzaDto.setNom("Margherita");

        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(10L);
        ingredientDto.setName("Cheese");
        ingredientDto.setPrix(5f);

        PizzaCommandeDto pizzaCommandeDto = new PizzaCommandeDto();
        pizzaCommandeDto.setId(1L);
        pizzaCommandeDto.setPizza(pizzaDto);
        pizzaCommandeDto.setIngredients(Collections.singletonList(ingredientDto));

        List<PizzaCommandeDto> pizzaCommandeDtos = Collections.singletonList(pizzaCommandeDto);

        statistique.setStatPizza(new HashMap<>());
        statistique.setStatIngredient(new HashMap<>());
        when(statistiqueRepository.findById("1")).thenReturn(Optional.of(statistique));

        when(statistiqueRepository.save(any(Statistique.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(statistiqueMapper.toDto(any(Statistique.class))).thenAnswer(invocation -> {
            Statistique stat = invocation.getArgument(0);
            StatistiqueDto dto = new StatistiqueDto();
            dto.setId(stat.getId());
            dto.setStatPizza(stat.getStatPizza());
            dto.setStatIngredient(stat.getStatIngredient());
            return dto;
        });

        StatistiqueDto result = statistiqueService.updateStatistiqueList(pizzaCommandeDtos);
        assertNotNull(result);
        assertEquals("1", result.getId());

        assertTrue(result.getStatPizza().containsKey("Margherita"));
        assertEquals(1, result.getStatPizza().get("Margherita").intValue());

        assertTrue(result.getStatIngredient().containsKey("Cheese"));
        assertEquals(1, result.getStatIngredient().get("Cheese").intValue());
    }
}