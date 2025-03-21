package com.example.demo.controllers;

import com.example.demo.dtos.IngredientDto;
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
    public ResponseEntity<IngredientDto> addIngredient(@RequestBody IngredientDto ingredientDto) {
        IngredientDto createdIngredient = ingredientService.addIngredient(ingredientDto);
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
    public ResponseEntity<IngredientDto> updateIngredient(@PathVariable long id, @RequestBody IngredientDto ingredientDto) {
        IngredientDto updatedIngredient = ingredientService.updateIngredient(id, ingredientDto);
        if (updatedIngredient != null) {
            return ResponseEntity.ok(updatedIngredient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable long id) {
        String response = ingredientService.deleteIngredient(id);

        if ("Ingredient supprimé".equals(response)) {
            return ResponseEntity.ok(response);
        } else if ("Ingredient non supprimé".equals(response)) {
            return ResponseEntity.badRequest().body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
