package com.example.demo.mappers;

import com.example.demo.dtos.StandardDto;
import com.example.demo.entities.Standard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StandardMapper {
    StandardDto toDto(Standard standard);
    Standard toEntity(StandardDto standardDto);
}