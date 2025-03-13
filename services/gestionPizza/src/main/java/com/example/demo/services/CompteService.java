package com.example.demo.services;

import com.example.demo.dtos.CompteDto;
import java.util.List;

public interface CompteService {
    CompteDto createCompte(CompteDto compteDto);
    CompteDto updateCompte(long id, CompteDto compteDto);
    void deleteCompte(long id);
    CompteDto getCompteById(long id);
    List<CompteDto> getAllComptes();
}