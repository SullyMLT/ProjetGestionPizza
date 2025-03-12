package com.example.demo.controllers;

import com.example.demo.dtos.PizzaDto;
import com.example.demo.entities.Pizza;
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
    public ResponseEntity<PizzaDto> addPizza(@RequestBody Pizza pizza) {
        PizzaDto newPizza = pizzaService.addPizza(pizza);
        return ResponseEntity.ok(newPizza);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PizzaDto> updatePizza(@PathVariable Long id, @RequestBody Pizza pizza) {
        PizzaDto updatedPizza = pizzaService.updatePizza(id, pizza);
        if (updatedPizza != null) {
            return ResponseEntity.ok(updatedPizza);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
        return ResponseEntity.noContent().build();
    }
}