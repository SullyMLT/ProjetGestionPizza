package com.example.demo.services.impl;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.dtos.CompteDto;
import com.example.demo.entities.Commande;
import com.example.demo.entities.Compte;
import com.example.demo.mappers.CompteMapperImpl;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.repositories.CompteRepository;
import com.example.demo.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompteServiceImpl implements CompteService {

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private CompteMapperImpl compteMapperImpl;

    @Override
    public CompteDto createCompte(CompteDto compteDto) {
        Compte compte = this.compteMapperImpl.toEntity(compteDto);
        List<CompteDto> compteDtos = this.getAllComptes();
        if (compteDtos != null) {
            for (CompteDto c : compteDtos) {
                if (c.getUsername().equals(compteDto.getUsername())) {
                    return null;
                }
            }
        }
        if (compte.getRole().equals(null)){
            compte.setRole("client");
        }
        compte.setActiver(true);
        Compte savedCompte = compteRepository.save(compte);
        return this.compteMapperImpl.toDto(savedCompte);
    }

    @Override
    public CompteDto updateCompte(Long id, CompteDto compteDto) {
        Optional<Compte> optionalCompte = this.compteRepository.findById(Math.toIntExact(id));
        if (optionalCompte.isPresent()) {
            Compte existingCompte = optionalCompte.get();
            existingCompte.setUsername(compteDto.getUsername());
            existingCompte.setPassword(compteDto.getPassword());
            Compte updatedCompte = compteRepository.save(existingCompte);
            return this.compteMapperImpl.toDto(updatedCompte);
        } else {
            System.out.println("problème updateCompte");
            return null;
        }
    }

    @Override
    public CompteDto enableDisableCompte(Long id) {
        Optional<Compte> optionalCompte = this.compteRepository.findById(Math.toIntExact(id));
        if (optionalCompte.isPresent()) {
            Compte existingCompte = optionalCompte.get();
            existingCompte.setActiver(!existingCompte.isActiver());
            Compte updatedCompte = compteRepository.save(existingCompte);
            return this.compteMapperImpl.toDto(updatedCompte);
        } else {
            System.out.println("problème activateCompte");
            return null;
        }
    }

    @Override
    public void deleteCompte(Long id) {
        compteRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public CompteDto getCompteById(Long id) {
        Optional<Compte> optionalCompte = compteRepository.findById(Math.toIntExact(id));
        if (optionalCompte.isPresent()) {
            return this.compteMapperImpl.toDto(optionalCompte.get());
        } else {
            System.out.println("problème getByIdCompte");
            return null;
        }
    }

    @Override
    public List<CompteDto> getAllComptes() {
        List<Compte> comptes = this.compteRepository.findAll();
        List<CompteDto> compteDtos = new ArrayList<>();
        if (comptes.isEmpty()) {
            System.out.println("Liste de compte vide");
            return null;
        } else {
            for (Compte compte : comptes) {
                compteDtos.add(this.compteMapperImpl.toDto(compte));
            }
            return compteDtos;
        }
    }

    @Override
    public CompteDto connexion(String username) {
        List<Compte> comptes = this.compteRepository.findAll();
        for (Compte compte : comptes) {
            if (compte.getUsername().equals(username)) {
                return compteMapperImpl.toDto(compte);
            }
        }
        CompteDto compteDto = new CompteDto();
        compteDto.setId(-1L);
        return compteDto;
    }

    @Override
    public String getUsernameByCompteId(Long compteId) {
        Optional<Compte> optionalCompte = this.compteRepository.findById(Math.toIntExact(compteId));
        if (optionalCompte.isPresent()){
            Compte compte = optionalCompte.get();
            if (!compte.isActiver()){
                return "Utilisateur Supprimé";
            }
            return compte.getUsername();
        }
        return "Utilisateur non trouvé";
    }
}