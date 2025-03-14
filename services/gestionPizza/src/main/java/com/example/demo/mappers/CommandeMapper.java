// src/main/java/com/example/demo/mappers/CommandeMapper.java
package com.example.demo.mappers;

import com.example.demo.dtos.CommandeDto;
import com.example.demo.entities.Commande;

public interface CommandeMapper {
    Commande toEntity(CommandeDto dto);
    CommandeDto toDto(Commande entity);
}