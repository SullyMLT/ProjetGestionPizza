package com.example.demo.controllers;

import com.example.demo.auth.AuthRequest;
import com.example.demo.dtos.CompteDto;
import com.example.demo.services.impl.CompteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comptes")
public class CompteController {

    @Autowired
    private CompteServiceImpl compteServiceImpl;

    @PostMapping
    public ResponseEntity<CompteDto> createCompte(@RequestBody CompteDto compteDto) {
        CompteDto createdCompte = compteServiceImpl.createCompte(compteDto);
        return ResponseEntity.ok(createdCompte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompteDto> updateCompte(@PathVariable long id, @RequestBody CompteDto compteDto) {
        CompteDto updatedCompte = compteServiceImpl.updateCompte(id, compteDto);
        if (updatedCompte != null) {
            return ResponseEntity.ok(updatedCompte);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable long id) {
        compteServiceImpl.deleteCompte(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompteDto> getCompteById(@PathVariable long id) {
        CompteDto compteDto = compteServiceImpl.getCompteById(id);
        if (compteDto != null) {
            return ResponseEntity.ok(compteDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/connexion")
    public ResponseEntity<CompteDto> connexion(@RequestBody AuthRequest authRequest) {
        CompteDto isAuth = compteServiceImpl.connexion(authRequest.getUsername(), authRequest.getPassword());

        if (isAuth.getId()>-1) {
            return ResponseEntity.ok(isAuth);
        } else {
            return ResponseEntity.status(401).body(isAuth);
        }
    }

    @GetMapping
    public ResponseEntity<List<CompteDto>> getAllComptes() {
        List<CompteDto> compteDtos = compteServiceImpl.getAllComptes();
        if (compteDtos != null && !compteDtos.isEmpty()) {
            return ResponseEntity.ok(compteDtos);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}