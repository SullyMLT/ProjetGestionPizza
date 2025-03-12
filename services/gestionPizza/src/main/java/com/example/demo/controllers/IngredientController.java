package com.example.demo.controllers;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.entities.Ingredient;
import com.example.demo.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<IngredientDto> addIngredient(@RequestBody Ingredient ingredient) {
        IngredientDto createdIngredient = ingredientService.addIngredient(ingredient);
        return ResponseEntity.status(201).body(createdIngredient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDto> getIngredientById(@PathVariable long id) {
        IngredientDto ingredientDto = ingredientService.getIngredientById(id);
        if (ingredientDto != null) {
            return ResponseEntity.ok(ingredientDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<IngredientDto>> getAllIngredients() {
        List<IngredientDto> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientDto> updateIngredient(@PathVariable long id, @RequestBody Ingredient ingredient) {
        IngredientDto updatedIngredient = ingredientService.updateIngredient(id, ingredient);
        if (updatedIngredient != null) {
            return ResponseEntity.ok(updatedIngredient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}