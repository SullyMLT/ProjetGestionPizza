package com.example.demo.services;

import com.example.demo.dtos.StandardDto;
import java.util.List;

public interface StandardService {
    StandardDto addStandard(StandardDto standardDto);
    boolean deleteStandard(Long id);
    StandardDto updateStandard(Long id, StandardDto standardDto);
    StandardDto getStandardById(Long id);
    StandardDto getStandardByPizzaId(Long id);
    List<StandardDto> getAllStandards();
}