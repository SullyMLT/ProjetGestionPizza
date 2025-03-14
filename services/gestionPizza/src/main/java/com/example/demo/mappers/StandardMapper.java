package com.example.demo.mappers;

import com.example.demo.dtos.StandardDto;
import com.example.demo.entities.Standard;

public interface StandardMapper {
    StandardDto toDto(Standard standard);
    Standard toEntity(StandardDto standardDto);
}