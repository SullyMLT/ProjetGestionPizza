package com.example.demo.services.impl;

import com.example.demo.entities.Commande;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpl implements CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public void addCommande(Commande commande) {
        commandeRepository.save(commande);
    }

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public Optional<Commande> getCommandeById(Long id) {
        return commandeRepository.findById(Math.toIntExact(id));
    }

    public void updateCommande(Long id, Commande updatedCommande) {
        if (commandeRepository.existsById(Math.toIntExact(id))) {
            updatedCommande.setId(id);
            commandeRepository.save(updatedCommande);
        }
    }

    public void deleteCommande(Long id) {
        commandeRepository.deleteById(Math.toIntExact(id));
    }
}