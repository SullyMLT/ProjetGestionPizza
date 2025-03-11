package com.example.demo.controllers;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.services.impl.CommandeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeServiceImpl commandeServiceImpl;

    @GetMapping
    public List<CommandeDto> getAllCommandes() {
        return commandeServiceImpl.getAllCommandes();
    }

    @GetMapping("/{id}")
    public CommandeDto getCommandeById(@PathVariable Long id) {
        return commandeServiceImpl.getCommandeById(id);
    }

    @PostMapping
    public CommandeDto createCommande(@RequestBody CommandeDto commandeDto) {
        return commandeServiceImpl.addCommande(commandeDto);
    }
}