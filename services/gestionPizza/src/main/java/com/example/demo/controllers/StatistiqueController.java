package com.example.demo.controllers;

import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.dtos.StatistiqueDto;
import com.example.demo.services.impl.StatistiqueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistiques")
public class StatistiqueController {

    @Autowired
    private StatistiqueServiceImpl statistiqueServiceImpl;

    @GetMapping
    public ResponseEntity<StatistiqueDto> getStatistique() {
        StatistiqueDto statistiqueDto = statistiqueServiceImpl.getStatistique();
        return ResponseEntity.ok(statistiqueDto);
    }
}