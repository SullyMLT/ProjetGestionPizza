package com.example.demo.services.impl;

import com.example.demo.dtos.IngredientDto;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.PizzaCommande;
import com.example.demo.entities.Standard;
import com.example.demo.mappers.IngredientMapperImpl;
import com.example.demo.repositories.IngredientRepository;
import com.example.demo.repositories.PizzaCommandeRepository;
import com.example.demo.repositories.StandardRepository;
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
    @Autowired
    private IngredientMapperImpl ingredientMapperImpl;
    @Autowired
    private StandardRepository standardRepository;
    @Autowired
    private PizzaCommandeRepository pizzaCommandeRepository;

    @Override
    public IngredientDto addIngredient(IngredientDto ingredientDto) {
        // conversion Dto to entity
        Ingredient ingredient = this.ingredientMapperImpl.toEntity(ingredientDto);
        // sauvegarde de l'entité sur la base
        Ingredient saveIngredient = this.ingredientRepository.save(ingredient);

        return this.ingredientMapperImpl.toDto(saveIngredient);
    }

    @Override
    public String deleteIngredient(Long id) {
        IngredientDto ingredientDto = getIngredientById(id);
        Ingredient ingre = this.ingredientMapperImpl.toEntity(ingredientDto);
        if (ingredientDto != null) {
            List<PizzaCommande> pizzaCom = this.pizzaCommandeRepository.findAll();
            for (PizzaCommande pizzaCommande : pizzaCom) {
                if (pizzaCommande.getIngredients().contains(ingre)) {
                    return "L'ingredient ne peut pas être supprimé";
                }
            }
        }
        List<Standard> standard = this.standardRepository.findAll();
        for (Standard stand : standard) {
            if (stand.getIngredients().contains(ingre)) {
                stand.getIngredients().remove(ingre);
                this.standardRepository.save(stand);
            }
        }

        ingredientRepository.deleteById(Math.toIntExact(id));
        Optional<Ingredient> DelIngre = ingredientRepository.findById(Math.toIntExact(id));
        if (DelIngre.isPresent()) {
            return "Ingredient non supprimé";
        }else{
            return "Ingredient supprimé";
        }
    }

    @Override
    public IngredientDto updateIngredient(Long id, IngredientDto ingredientDto) {

        Optional<Ingredient> optionalIngredient = this.ingredientRepository.findById(Math.toIntExact(id));

        if (optionalIngredient.isPresent()) {
            // donnée à mettre à jour sur la base
            Ingredient ingredientToUpdate = this.ingredientMapperImpl.toEntity(ingredientDto);
            // donnée récupérée de la base
            Ingredient ingredientUpdated = optionalIngredient.get();
            // conversion de la donnée à mettre à jour en entité
            ingredientUpdated.setName(ingredientToUpdate.getName());
            ingredientUpdated.setDescription(ingredientToUpdate.getDescription());
            ingredientUpdated.setPhoto(ingredientToUpdate.getPhoto());
            ingredientUpdated.setPrix(ingredientToUpdate.getPrix());

            // sauvegarde des données
            Ingredient savedIngredient = this.ingredientRepository.save(ingredientUpdated);
            return this.ingredientMapperImpl.toDto(savedIngredient);
        }
        System.out.println("Ingredient problème update");
        return null;
    }

    @Override
    public IngredientDto getIngredientById(Long id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(Math.toIntExact(id));
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