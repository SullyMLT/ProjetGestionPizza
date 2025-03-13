package com.example.demo.services.impl;

import com.example.demo.dtos.PizzaCommandeDto;
import com.example.demo.entities.*;
import com.example.demo.mappers.PizzaCommandeMapper;
import com.example.demo.repositories.*;
import com.example.demo.services.PizzaCommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaCommandeServiceImpl implements PizzaCommandeService {

    @Autowired
    private PizzaCommandeRepository pizzaCommandeRepository;

    @Autowired
    private PizzaCommandeMapper pizzaCommandeMapper;

    @Override
    public PizzaCommandeDto createPizzaCommande(PizzaCommandeDto pizzaCommandeDto) {
        PizzaCommande pizzaCommande = pizzaCommandeMapper.toEntity(pizzaCommandeDto);
        PizzaCommande savedPizzaCommande = pizzaCommandeRepository.save(pizzaCommande);
        return pizzaCommandeMapper.toDto(savedPizzaCommande);
    }

    @Override
    public PizzaCommandeDto getPizzaCommandeById(long id) {
        Optional<PizzaCommande> pizzaCommande = pizzaCommandeRepository.findById(id);
        return pizzaCommande.isPresent() ? pizzaCommandeMapper.toDto(pizzaCommande.get()) : null;

    }

    @Override
    public List<PizzaCommandeDto> getAllPizzaCommandes() {
        List<PizzaCommande> pizzaCommandes = this.pizzaCommandeRepository.findAll();
        List<PizzaCommandeDto> pizzaCommandeDtos = new ArrayList<>();
        for (PizzaCommande pizzaCommande : pizzaCommandes) {
            // Map entity to DTO
            pizzaCommandeDtos.add(this.pizzaCommandeMapper.toDto(pizzaCommande));
        }
        return pizzaCommandeDtos;
    }

    @Override
    public void deletePizzaCommande(long id) {
        pizzaCommandeRepository.deleteById(id);
    }
}