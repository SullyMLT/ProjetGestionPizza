package com.example.demo.mappers;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.entities.Commande;
import org.springframework.stereotype.Component;

@Component
public class CommandeMapperImpl {

    public CommandeDto toDto(Commande commande){
        if (commande == null) {
            return null;
        }
        CommandeDto commandeDto = new CommandeDto();
        commandeDto.setId(commande.getId());
        commandeDto.setPrix(commande.getPrix());
        commandeDto.setDescription(commande.getDescription());
        commandeDto.setDate(commande.getDate());
        commandeDto.setValidation(commande.isValidation());
        return commandeDto;
    }

    public Commande toEntity(CommandeDto commandeDto){
        if (commandeDto == null) {
            return null;
        }
        Commande commande = new Commande();
        commande.setId(commandeDto.getId());
        commande.setPrix(commandeDto.getPrix());
        commande.setDescription(commandeDto.getDescription());
        commande.setDate(commandeDto.getDate());
        commande.setValidation(commandeDto.isValidation());
        return commande;
    }
}