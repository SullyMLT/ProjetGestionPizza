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
    public IngredientDto addIngredient(Ingredient ingredient) {
        Ingredient saveIngredient = ingredientRepository.save(ingredient);

        return new IngredientDto(saveIngredient);
    }

    @Override
    public void deleteIngredient(long id) {
        ingredientRepository.deleteById((int) id);
    }

    @Override
    public IngredientDto updateIngredient(long id, Ingredient ingredient) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById((int) id);
        if (optionalIngredient.isPresent()) {
            Ingredient ingredientUpdated = optionalIngredient.get();
            ingredient.setName(ingredientUpdated.getName());
            ingredient.setDescription(ingredientUpdated.getDescription());
            ingredient.setPathPhoto(ingredientUpdated.getPathPhoto());
            ingredient.setPrix(ingredientUpdated.getPrix());
            ingredientRepository.save(ingredientUpdated);
            return new IngredientDto(ingredientUpdated);
        }
        System.out.println("Ingredient problème update");
        return null;
    }

    @Override
    public IngredientDto getIngredientById(long id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById((int) id);
        if (optionalIngredient.isPresent()){
            return new IngredientDto(optionalIngredient.get());
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
            ingredientDtos.add(new IngredientDto(ingredient));
        }
        return ingredientDtos;
    }
}