package com.example.demo.controllers;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public ResponseEntity<List<PizzaDto>> getAllPizzas() {
        List<PizzaDto> pizzas = pizzaService.getAllPizzas();
        return ResponseEntity.ok(pizzas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PizzaDto> getPizzaById(@PathVariable Long id) {
        PizzaDto getPizza = pizzaService.getPizzaById(id);
        if (getPizza != null) {
            return ResponseEntity.ok(getPizza);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PizzaDto> addPizza(@RequestBody PizzaDto pizzaDto) {
        PizzaDto newPizza = pizzaService.addPizza(pizzaDto);
        return ResponseEntity.ok(newPizza);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PizzaDto> updatePizza(@PathVariable Long id, @RequestBody PizzaDto pizzaDto) {
        PizzaDto updatedPizza = pizzaService.updatePizza(id, pizzaDto);
        if (updatedPizza != null) {
            return ResponseEntity.ok(updatedPizza);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public boolean deletePizza(@PathVariable Long id) {
        return pizzaService.deletePizza(id);
    }
}