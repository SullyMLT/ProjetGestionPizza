package com.example.demo.configuration;

import com.example.demo.entities.Commande;
import com.example.demo.entities.Commentaire;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Pizza;
import com.example.demo.entities.PizzaCommande;
import com.example.demo.entities.Standard;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.repositories.CommentaireRepository;
import com.example.demo.repositories.IngredientRepository;
import com.example.demo.repositories.PizzaCommandeRepository;
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
    private PizzaRepository pizzaRepository;

    @Autowired
    private StandardRepository standardRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private PizzaCommandeRepository pizzaCommandeRepository;

    @Override
    public void run(String... args) throws Exception {

        // Create Ingredients
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Sauce tomate");
        ingredient1.setDescription("Description 1");
        ingredient1.setPathPhoto("path1.jpg");
        ingredient1.setPrix(10.0);
        ingredientRepository.save(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Crême fraiche");
        ingredient2.setDescription("Description 2");
        ingredient2.setPathPhoto("/pizza-1498148703.jpg");
        ingredient2.setPrix(20.0);
        ingredientRepository.save(ingredient2);

        // Create Standards
        Standard standard1 = new Standard();
        standard1.setIngredients(Arrays.asList(ingredient1, ingredient2));
        standardRepository.save(standard1);

        Standard standard2 = new Standard();
        standard2.setIngredients(Arrays.asList(ingredient1));
        standardRepository.save(standard2);

        // Create Pizzas
        Pizza pizza1 = new Pizza();
        pizza1.setNom("Pizza Peperoni");
        pizza1.setDescription("Description 1");
        pizza1.setPhoto("/Pepperoni_Pizza_Beauty_1200x1200.webp");
        pizza1.setPrix(15.0f);
        pizza1.setStandard(standard1);
        pizzaRepository.save(pizza1);

        Pizza pizza2 = new Pizza();
        pizza2.setNom("Pizza Margherita");
        pizza2.setDescription("Description 2");
        pizza2.setPhoto("/pizza-1498148703.jpg");
        pizza2.setPrix(25.0f);
        pizza2.setStandard(standard2);
        pizzaRepository.save(pizza2);

        // Create Commandes
        Commande commande1 = new Commande();
        commande1.setDescription("Commande 1");
        commande1.setValidation(1);
        commande1.setDate("2023-10-01");
        commande1.setPrix(40.0f);
        commandeRepository.save(commande1);

        // Create PizzaCommandes
        PizzaCommande pizzaCommande1 = new PizzaCommande();
        pizzaCommande1.setCommande(commande1);
        pizzaCommande1.setPizza(pizza1);
        pizzaCommande1.setIngredients(Arrays.asList(ingredient1, ingredient2));
        pizzaCommandeRepository.save(pizzaCommande1);

        // Create Commentaires
        Commentaire commentaire1 = new Commentaire();
        commentaire1.setDescription("Très bonne pizza !");
        commentaire1.setDate("2023-10-01");
        commentaire1.setPizzaOrigine(pizza1.getId());
        commentaire1.setNote(5);
        commentaireRepository.save(commentaire1);
    }
}