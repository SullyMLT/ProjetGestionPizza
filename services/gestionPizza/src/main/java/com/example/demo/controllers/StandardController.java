package com.example.demo.controllers;

import com.example.demo.dtos.StandardDto;
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

    @PostMapping
    public ResponseEntity<StandardDto> addStandard(@RequestBody StandardDto standardDto) {
        StandardDto createdStandard = standardService.addStandard(standardDto);
        return ResponseEntity.status(201).body(createdStandard);
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

    @GetMapping
    public ResponseEntity<List<StandardDto>> getAllStandards() {
        List<StandardDto> standards = standardService.getAllStandards();
        return ResponseEntity.ok(standards);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStandard(@PathVariable long id) {
        standardService.deleteStandard(id);
        return ResponseEntity.noContent().build();
    }
}