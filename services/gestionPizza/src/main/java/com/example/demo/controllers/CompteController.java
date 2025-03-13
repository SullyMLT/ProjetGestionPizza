package com.example.demo.controllers;

import com.example.demo.dtos.CompteDto;
import com.example.demo.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comptes")
public class CompteController {

    @Autowired
    private CompteService compteService;

    @PostMapping
    public ResponseEntity<CompteDto> createCompte(@RequestBody CompteDto compteDto) {
        CompteDto createdCompte = compteService.createCompte(compteDto);
        return ResponseEntity.ok(createdCompte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompteDto> updateCompte(@PathVariable long id, @RequestBody CompteDto compteDto) {
        CompteDto updatedCompte = compteService.updateCompte(id, compteDto);
        if (updatedCompte != null) {
            return ResponseEntity.ok(updatedCompte);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable long id) {
        compteService.deleteCompte(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompteDto> getCompteById(@PathVariable long id) {
        CompteDto compteDto = compteService.getCompteById(id);
        if (compteDto != null) {
            return ResponseEntity.ok(compteDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CompteDto>> getAllComptes() {
        List<CompteDto> compteDtos = compteService.getAllComptes();
        if (compteDtos != null && !compteDtos.isEmpty()) {
            return ResponseEntity.ok(compteDtos);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}