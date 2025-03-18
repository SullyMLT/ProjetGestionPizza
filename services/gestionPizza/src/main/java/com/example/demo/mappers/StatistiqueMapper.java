package com.example.demo.mappers;

import com.example.demo.dtos.StatistiqueDto;
import com.example.demo.entities.Statistique;

public interface StatistiqueMapper {
    StatistiqueDto toDto(Statistique statistique);
    Statistique toEntity(StatistiqueDto statistiqueDto);
}