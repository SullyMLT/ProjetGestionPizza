package com.example.demo.controllers;

import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.services.PizzaCommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizzaCommandes")
public class PizzaCommandeController {

    @Autowired
    private PizzaCommandeService pizzaCommandeService;

    @PostMapping
    public ResponseEntity<PizzaCommandeDto> createPizzaCommande(@RequestBody PizzaCommandeDto pizzaCommandeDto) {
        PizzaCommandeDto createdPizzaCommande = pizzaCommandeService.createPizzaCommande(pizzaCommandeDto);
        return ResponseEntity.status(201).body(createdPizzaCommande);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PizzaCommandeDto> getPizzaCommandeById(@PathVariable long id) {
        PizzaCommandeDto pizzaCommandeDto = pizzaCommandeService.getPizzaCommandeById(id);
        if (pizzaCommandeDto != null) {
            return ResponseEntity.ok(pizzaCommandeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PizzaCommandeDto>> getAllPizzaCommandes() {
        List<PizzaCommandeDto> pizzaCommandes = pizzaCommandeService.getAllPizzaCommandes();
        return ResponseEntity.ok(pizzaCommandes);
    }

    @DeleteMapping("/{id}")
    public boolean deletePizzaCommande(@PathVariable long id) {
        return pizzaCommandeService.deletePizzaCommande(id);
    }

    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<PizzaCommandeDto>> getPizzaCommandeByCommandeId(@PathVariable long commandeId) {
        List<PizzaCommandeDto> pizzaCommandes = pizzaCommandeService.getPizzaCommandeByCommandeId(commandeId);
        if (pizzaCommandes == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pizzaCommandes);
    }
}