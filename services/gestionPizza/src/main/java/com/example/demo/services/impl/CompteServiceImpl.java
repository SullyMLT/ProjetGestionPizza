package com.example.demo.services.impl;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.dtos.CompteDto;
import com.example.demo.entities.Commande;
import com.example.demo.entities.Compte;
import com.example.demo.mappers.impl.CompteMapperImpl;
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
    private CommandeRepository commandeRepository;

    @Autowired
    private CompteMapperImpl compteMapperImpl;

    @Override
    public CompteDto createCompte(CompteDto compteDto) {
        Compte compte = this.compteMapperImpl.toEntity(compteDto);
        Compte savedCompte = compteRepository.save(compte);
        return this.compteMapperImpl.toDto(savedCompte);
    }

    @Override
    public CompteDto updateCompte(long id, CompteDto compteDto) {
        Optional<Compte> optionalCompte = this.compteRepository.findById((int) id);
        if (optionalCompte.isPresent()) {
            Compte existingCompte = optionalCompte.get();
            existingCompte.setUsername(compteDto.getUsername());
            existingCompte.setPassword(compteDto.getPassword());
            List<Commande> commandes = new ArrayList<>();
            for (CommandeDto commandeDto : compteDto.getCommandeIds()) {
                Optional<Commande> com = commandeRepository.findById(Math.toIntExact(commandeDto.getId()));
                if (com.isPresent()) {
                    commandes.add(com.get());
                }
            }
            existingCompte.setCommandes(commandes);
            Compte updatedCompte = compteRepository.save(existingCompte);
            return this.compteMapperImpl.toDto(updatedCompte);
        } else {
            System.out.println("problème updateCompte");
            return null;
        }
    }

    @Override
    public void deleteCompte(long id) {
        compteRepository.deleteById((int) id);
    }

    @Override
    public CompteDto getCompteById(long id) {
        Optional<Compte> optionalCompte = compteRepository.findById((int) id);
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
    public CompteDto connexion(String username, String password) {
        List<Compte> comptes = this.compteRepository.findAll();
        for (Compte compte : comptes) {
            if (compte.getUsername().equals(username) && compte.getPassword().equals(password)) {
                return compteMapperImpl.toDto(compte);
            }
        }
        CompteDto compteDto = new CompteDto();
        compteDto.setId(-1);
        return compteDto;
    }
}