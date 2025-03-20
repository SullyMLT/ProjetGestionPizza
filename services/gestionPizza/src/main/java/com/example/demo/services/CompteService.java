package com.example.demo.services;

import com.example.demo.dtos.CompteDto;
import java.util.List;

public interface CompteService {
    CompteDto createCompte(CompteDto compteDto);
    CompteDto updateCompte(Long id, CompteDto compteDto);
    CompteDto enableDisableCompte(Long id);
    void deleteCompte(Long id);
    CompteDto getCompteById(Long id);
    List<CompteDto> getAllComptes();
    CompteDto connexion(String username, String password);

    String getUsernameByCompteId(Long compteId);
}