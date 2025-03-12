package com.example.demo.configuration;

import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Optionnel;
import com.example.demo.entities.Pizza;
import com.example.demo.entities.Standard;
import com.example.demo.repositories.IngredientRepository;
import com.example.demo.repositories.OptionnelRepository;
import com.example.demo.repositories.PizzaRepository;
import com.example.demo.repositories.StandardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private OptionnelRepository optionnelRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private StandardRepository standardRepository;

    @Override
    public void run(String... args) throws Exception {

        // Delete all existing data
        ingredientRepository.deleteAll();
        pizzaRepository.deleteAll();
        standardRepository.deleteAll();
        optionnelRepository.deleteAll();



        // Create Optionnels
        Optionnel optionnel1 = new Optionnel();
        optionnel1.setName("Optionnel 1");
        Optionnel optionnel2 = new Optionnel();
        optionnel2.setName("Optionnel 2");
        optionnelRepository.saveAll(Arrays.asList(optionnel1, optionnel2));

        // Create Standards
        Standard standard1 = new Standard();
        standard1.setName("Standard 1");
        Standard standard2 = new Standard();
        standard2.setName("Standard 2");
        standardRepository.saveAll(Arrays.asList(standard1, standard2));

        // Create Ingredients
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Ingredient 1");
        ingredient1.setDescription("Description 1");
        ingredient1.setPathPhoto("path1.jpg");
        ingredient1.setPrix(10.0);
        ingredient1.setOptionnels(Arrays.asList(optionnel1, optionnel2));
        ingredient1.setStandards(Arrays.asList(standard1, standard2));
        ingredientRepository.save(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Ingredient 2");
        ingredient2.setDescription("Description 2");
        ingredient2.setPathPhoto("/pizza-1498148703.jpg");
        ingredient2.setPrix(20.0);
        ingredient2.setOptionnels(Arrays.asList(optionnel1));
        ingredient2.setStandards(Arrays.asList(standard2));
        ingredientRepository.save(ingredient2);

        // Create Pizzas
        Pizza pizza1 = new Pizza();
        pizza1.setNom("Pizza 1");
        pizza1.setDescription("Description 1");
        pizza1.setPhoto("/Pepperoni_Pizza_Beauty_1200x1200.webp");
        pizza1.setPrix(15.0f);
        pizza1.setOptionnels(Arrays.asList(optionnel1, optionnel2));
        pizza1.setStandards(Arrays.asList(standard1, standard2));
        pizzaRepository.save(pizza1);

        Pizza pizza2 = new Pizza();
        pizza2.setNom("PEPERO- NICO-NIIII");
        pizza2.setDescription("Description 2");
        pizza2.setPhoto("/pizza-1498148703.jpg");
        pizza2.setPrix(25.0f);
        pizza2.setOptionnels(Arrays.asList(optionnel2));
        pizza2.setStandards(Arrays.asList(standard1));
        pizzaRepository.save(pizza2);
    }
}