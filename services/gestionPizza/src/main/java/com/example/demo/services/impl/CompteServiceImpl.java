package com.example.demo.services.impl;

import com.example.demo.dtos.CompteDto;
import com.example.demo.entities.Commande;
import com.example.demo.entities.Compte;
import com.example.demo.mappers.CompteMapper;
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
    private CompteMapper compteMapper;

    @Override
    public CompteDto createCompte(CompteDto compteDto) {
        Compte compte = this.compteMapper.toEntity(compteDto);
        Compte savedCompte = compteRepository.save(compte);
        return this.compteMapper.toDto(savedCompte);
    }

    @Override
    public CompteDto updateCompte(long id, CompteDto compteDto) {
        Optional<Compte> optionalCompte = this.compteRepository.findById((int) id);
        if (optionalCompte.isPresent()) {
            Compte existingCompte = optionalCompte.get();
            existingCompte.setUsername(compteDto.getUsername());
            existingCompte.setPassword(compteDto.getPassword());
            List<Commande> commandes = new ArrayList<>();
            for (Long commandeId : compteDto.getCommandeIds()) {
                Optional<Commande> com = commandeRepository.findById(Math.toIntExact(commandeId));
                if (com.isPresent()) {
                    commandes.add(com.get());
                }
            }
            existingCompte.setCommandes(commandes);
            Compte updatedCompte = compteRepository.save(existingCompte);
            return this.compteMapper.toDto(updatedCompte);
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
            return this.compteMapper.toDto(optionalCompte.get());
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
                compteDtos.add(this.compteMapper.toDto(compte));
            }
            return compteDtos;
        }
    }
}