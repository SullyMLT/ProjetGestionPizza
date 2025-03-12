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
        // conversion Dto to entity
        Ingredient ingredient = ingredientDto.toEntity();
        // sauvegarde de l'entité sur la base
        Ingredient saveIngredient = ingredientRepository.save(ingredient);

        return new IngredientDto(saveIngredient);
    }

    @Override
    public void deleteIngredient(long id) {
        ingredientRepository.deleteById((int) id);
    }

    @Override
    public IngredientDto updateIngredient(long id, IngredientDto ingredientDto) {

        Optional<Ingredient> optionalIngredient = ingredientRepository.findById((int) id);

        if (optionalIngredient.isPresent()) {
            // donnée à mettre à jour sur la base
            IngredientDto ingredientDtoUpdated = ingredientDto;
            // donnée récupérée de la base
            Ingredient ingredientUpdated = optionalIngredient.get();
            // conversion de la donnée à mettre à jour en entité
            Ingredient ingredientUpdated2 = ingredientDtoUpdated.toEntity();
            // mise à jour des données
            ingredientUpdated.setName(ingredientUpdated2.getName());
            ingredientUpdated.setDescription(ingredientUpdated2.getDescription());
            ingredientUpdated.setPathPhoto(ingredientUpdated2.getPathPhoto());
            ingredientUpdated.setPrix(ingredientUpdated2.getPrix());
            // sauvegarde des données
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