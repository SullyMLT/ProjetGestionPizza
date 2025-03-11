package com.example.demo.services;

import com.example.demo.entities.Optionnel;
import java.util.List;
import java.util.Optional;

public interface OptionnelService {
    List<Optionnel> getAllOptionnels();
    Optional<Optionnel> getOptionnelById(Long id);
    Optionnel createOptionnel(Optionnel optionnel);
    Optionnel updateOptionnel(Long id, Optionnel optionnel);
    void deleteOptionnel(Long id);
}