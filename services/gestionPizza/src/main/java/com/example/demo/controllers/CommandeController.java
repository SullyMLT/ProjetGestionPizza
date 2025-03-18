package com.example.demo.controllers;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.services.impl.CommandeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeServiceImpl commandeServiceImpl;

    @GetMapping
    public ResponseEntity<List<CommandeDto>> getAllCommandes() {
        List<CommandeDto> commandes = commandeServiceImpl.getAllCommandes();
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeDto> getCommandeById(@PathVariable Long id) {
        Optional<CommandeDto> commandeDto = Optional.ofNullable(commandeServiceImpl.getCommandeById(id));
        return commandeDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CommandeDto> createCommande(@RequestBody CommandeDto commandeDto, Long compteId) {
        CommandeDto createdCommande = commandeServiceImpl.addCommande(commandeDto, compteId);
        return ResponseEntity.status(201).body(createdCommande);
    }

    @PutMapping("/validation/{id}")
    public ResponseEntity<CommandeDto> validateCommande(@PathVariable Long id) {
        CommandeDto commandeDto = commandeServiceImpl.validateCommande(id);
        return ResponseEntity.ok(commandeDto);
    }
}