package com.example.demo.controllers;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.dtos.PizzaDto;
import com.example.demo.dtos.StandardDto;
import com.example.demo.mappers.PizzaMapperImpl;
import com.example.demo.repositories.PizzaRepository;
import com.example.demo.services.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/standards")
public class StandardController {

    @Autowired
    private StandardService standardService;

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private PizzaMapperImpl pizzaMapperImpl;

    @GetMapping
    public ResponseEntity<List<StandardDto>> getAllStandards() {
        List<StandardDto> standards = standardService.getAllStandards();
        return ResponseEntity.ok(standards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardDto> getStandardById(@PathVariable long id) {
        StandardDto standardDto = standardService.getStandardById(id);
        if (standardDto != null) {
            return ResponseEntity.ok(standardDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pizza/{pizzaId}")
    public ResponseEntity<StandardDto> getStandardByPizzaId(@PathVariable long pizzaId) {
        StandardDto standardDto = standardService.getStandardByPizzaId(pizzaId);
        if (standardDto != null) {
            return ResponseEntity.ok(standardDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<StandardDto> addStandard(@RequestBody StandardDto standardDto) {
        StandardDto createdStandard = standardService.addStandard(standardDto);
        return ResponseEntity.status(201).body(createdStandard);
    }



    @PutMapping("/{id}")
    public ResponseEntity<StandardDto> updateStandard(@PathVariable long id, @RequestBody StandardDto standardDto) {
        StandardDto updatedStandard = standardService.updateStandard(id, standardDto);
        if (updatedStandard != null) {
            return ResponseEntity.ok(updatedStandard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}