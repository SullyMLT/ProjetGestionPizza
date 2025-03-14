package com.example.demo.services.impl;

import com.example.demo.dtos.StandardDto;
import com.example.demo.entities.Pizza;
import com.example.demo.entities.Standard;
import com.example.demo.mappers.impl.StandardMapperImpl;
import com.example.demo.repositories.PizzaRepository;
import com.example.demo.repositories.StandardRepository;
import com.example.demo.services.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardRepository standardRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    private final StandardMapperImpl standardMapperImpl = new StandardMapperImpl();

    @Override
    public StandardDto addStandard(StandardDto standardDto) {
        Standard standard = standardMapperImpl.toEntity(standardDto);
        Standard savedStandard = standardRepository.save(standard);
        return standardMapperImpl.toDto(savedStandard);
    }

    @Override
    public void deleteStandard(long id) {
        standardRepository.deleteById(id);
    }

    @Override
    public StandardDto updateStandard(long id, StandardDto standardDto) {
        Optional<Standard> optionalStandard = standardRepository.findById(id);
        if (optionalStandard.isPresent()) {
            Standard standardToUpdate = standardMapperImpl.toEntity(standardDto);
            Standard standardUpdated = optionalStandard.get();
            standardUpdated.setIngredients(standardToUpdate.getIngredients());
            standardUpdated.setPizza(standardToUpdate.getPizza());

            Standard savedStandard = standardRepository.save(standardUpdated);
            return standardMapperImpl.toDto(savedStandard);
        }
        return null;
    }

    @Override
    public StandardDto getStandardById(long id) {
        Optional<Standard> optionalStandard = standardRepository.findById(id);
        return optionalStandard.isPresent() ? standardMapperImpl.toDto(optionalStandard.get()) : null;
    }

    @Override
    public List<StandardDto> getAllStandards() {
        List<Standard> standards = standardRepository.findAll();
        List<StandardDto> standardDtos = new ArrayList<>();
        for (Standard standard : standards) {
            standardDtos.add(standardMapperImpl.toDto(standard));
        }
        return standardDtos;
    }
}