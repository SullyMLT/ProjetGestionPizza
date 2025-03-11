package com.example.demo.services.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.entities.Ingredient;
import com.example.demo.repositories.IngredientRepository;
import com.example.demo.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public IngredientDto addIngredient(IngredientDto ingredientDto) {
        Ingredient ingredient = ingredientDto.toEntity();
        ingredientRepository.save(ingredient);
        return new IngredientDto().ingredientToDto(ingredient);
    }

    @Override
    public void deleteIngredient(long id) {
        ingredientRepository.deleteById((int) id);
    }

    @Override
    public IngredientDto updateIngredient(long id, IngredientDto ingredientDto) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById((int) id);
        if (optionalIngredient.isPresent()) {
            Ingredient ingredient = optionalIngredient.get();
            ingredient.setName(ingredientDto.getName());
            ingredient.setDescription(ingredientDto.getDescription());
            ingredient.setPathPhoto(ingredientDto.getPathPhoto());
            ingredient.setPrix(ingredientDto.getPrix());
            ingredientRepository.save(ingredient);
            return new IngredientDto().ingredientToDto(ingredient);
        }
        System.out.println("Ingredient problème update");
        return null;
    }

    @Override
    public IngredientDto getIngredientById(long id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById((int) id);
        if (optionalIngredient.isPresent()){
            return new IngredientDto().ingredientToDto(optionalIngredient.get());
        }else{
            System.out.println("Ingredient pas trouvé");
            return null;
        }
    }

    @Override
    public List<IngredientDto> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        List<IngredientDto> ingredientDtos = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientDtos.add(new IngredientDto().ingredientToDto(ingredient));
        }
        return ingredientDtos;
    }
}