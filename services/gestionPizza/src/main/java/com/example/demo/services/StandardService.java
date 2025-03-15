package com.example.demo.services;

import com.example.demo.dtos.StandardDto;
import java.util.List;

public interface StandardService {
    StandardDto addStandard(StandardDto standardDto);
    void deleteStandard(long id);
    StandardDto updateStandard(long id, StandardDto standardDto);
    StandardDto getStandardById(long id);
    StandardDto getStandardByPizzaId(long id);
    List<StandardDto> getAllStandards();
}