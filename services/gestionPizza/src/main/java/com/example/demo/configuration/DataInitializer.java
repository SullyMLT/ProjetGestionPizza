package com.example.demo;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(PizzaRepository pizzaRepository, IngredientRepository ingredientRepository,
                               CommandeRepository commandeRepository, CommentaireRepository commentaireRepository,
                               OptionnelRepository optionnelRepository, StandardRepository standardRepository,
                               PizzaCommandeRepository pizzaCommandeRepository) {
        return args -> {
            // Initialize Optionnel
            Optionnel optionnel = new Optionnel();
            optionnelRepository.save(optionnel);

            // Initialize Standard
            Standard standard = new Standard();
            standardRepository.save(standard);

            // Initialize Ingredients
            Ingredient ingredient1 = new Ingredient();
            ingredient1.setName("Tomato");
            ingredient1.setDescription("Fresh tomatoes");
            ingredient1.setPathPhoto("path/to/photo");
            ingredient1.setPrix(1.5);
            ingredient1.setOptionnel(optionnel);
            ingredient1.setStandard(standard);
            ingredientRepository.save(ingredient1);

            Ingredient ingredient2 = new Ingredient();
            ingredient2.setName("Cheese");
            ingredient2.setDescription("Mozzarella cheese");
            ingredient2.setPathPhoto("path/to/photo");
            ingredient2.setPrix(2.0);
            ingredient2.setOptionnel(optionnel);
            ingredient2.setStandard(standard);
            ingredientRepository.save(ingredient2);

            // Initialize Pizzas
            Pizza pizza1 = new Pizza();
            pizza1.setNom("Margherita");
            pizza1.setDescription("Classic Margherita pizza");
            pizza1.setPhoto("path/to/photo");
            pizza1.setPrix(8.0f);
            pizzaRepository.save(pizza1);

            Pizza pizza2 = new Pizza();
            pizza2.setNom("Pepperoni");
            pizza2.setDescription("Pepperoni pizza");
            pizza2.setPhoto("path/to/photo");
            pizza2.setPrix(10.0f);
            pizzaRepository.save(pizza2);

            // Initialize Commandes
            Commande commande1 = new Commande();
            commande1.setNumero(1);
            commande1.setDescription("First order");
            commande1.setValidation(1);
            commande1.setPizza_origine(1);
            commande1.setDate("2023-01-01");
            commande1.setPizzas(Arrays.asList(pizza1, pizza2));
            commandeRepository.save(commande1);

            // Initialize Commentaires
            Commentaire commentaire1 = new Commentaire();
            commentaire1.setDescription("Great pizza!");
            commentaire1.setDate("2023-01-01");
            commentaire1.setPizza_origine(1);
            commentaire1.setNote(5);
            commentaireRepository.save(commentaire1);

            // Initialize PizzaCommande
            PizzaCommande pizzaCommande1 = new PizzaCommande();
            pizzaCommande1.setCommande(commande1);
            pizzaCommande1.setIngredients(Arrays.asList(ingredient1, ingredient2));
            pizzaCommandeRepository.save(pizzaCommande1);
        };
    }
}