package com.example.demo.services.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.entities.Ingredient;
import com.example.demo.mappers.impl.IngredientMapperImpl;
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

    private final IngredientMapperImpl ingredientMapperImpl = new IngredientMapperImpl();

    @Override
    public IngredientDto addIngredient(IngredientDto ingredientDto) {
        // conversion Dto to entity
        Ingredient ingredient = this.ingredientMapperImpl.toEntity(ingredientDto);
        // sauvegarde de l'entité sur la base
        Ingredient saveIngredient = this.ingredientRepository.save(ingredient);

        return this.ingredientMapperImpl.toDto(saveIngredient);
    }

    @Override
    public void deleteIngredient(long id) {
        ingredientRepository.deleteById((int) id);
    }

    @Override
    public IngredientDto updateIngredient(long id, IngredientDto ingredientDto) {

        Optional<Ingredient> optionalIngredient = this.ingredientRepository.findById((int) id);

        if (optionalIngredient.isPresent()) {
            // donnée à mettre à jour sur la base
            Ingredient ingredientToUpdate = this.ingredientMapperImpl.toEntity(ingredientDto);
            // donnée récupérée de la base
            Ingredient ingredientUpdated = optionalIngredient.get();
            // conversion de la donnée à mettre à jour en entité
            ingredientUpdated.setName(ingredientToUpdate.getName());
            ingredientUpdated.setDescription(ingredientToUpdate.getDescription());
            ingredientUpdated.setPathPhoto(ingredientToUpdate.getPathPhoto());
            ingredientUpdated.setPrix(ingredientToUpdate.getPrix());

            // sauvegarde des données
            Ingredient savedIngredient = this.ingredientRepository.save(ingredientUpdated);
            return this.ingredientMapperImpl.toDto(savedIngredient);
        }
        System.out.println("Ingredient problème update");
        return null;
    }

    @Override
    public IngredientDto getIngredientById(long id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById((int) id);
        return optionalIngredient.isPresent() ? this.ingredientMapperImpl.toDto(optionalIngredient.get()): null;
    }

    @Override
    public List<IngredientDto> getAllIngredients() {
        List<Ingredient> ingredients = this.ingredientRepository.findAll();
        List<IngredientDto> ingredientDtos = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientDtos.add(this.ingredientMapperImpl.toDto(ingredient));
        }
        return ingredientDtos;
    }
}