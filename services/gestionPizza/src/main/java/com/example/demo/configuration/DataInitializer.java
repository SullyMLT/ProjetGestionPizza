package com.example.demo.configuration;

import com.example.demo.dtos.*;
import com.example.demo.entities.*;
import com.example.demo.mappers.impl.*;
import com.example.demo.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IngredientServiceImpl ingredientServiceImpl;
    private final PizzaServiceImpl pizzaServiceImpl;
    private final StandardServiceImpl standardServiceImpl;
    private final CommandeServiceImpl commandeServiceImpl;
    private final CommentaireServiceImpl commentaireServiceImpl;
    private final PizzaCommandeServiceImpl pizzaCommandeServiceImpl;
    private final IngredientMapperImpl ingredientMapper;
    private final StandardMapperImpl standardMapper;
    private final PizzaMapperImpl pizzaMapper;
    private final CommandeMapperImpl commandeMapper;
    private final PizzaCommandeMapperImpl pizzaCommandeMapper;
    private final CommentaireMapperImpl commentaireMapper;

    @Autowired
    public DataInitializer(
            IngredientServiceImpl ingredientServiceImpl,
            PizzaServiceImpl pizzaServiceImpl,
            StandardServiceImpl standardServiceImpl,
            CommandeServiceImpl commandeServiceImpl,
            CommentaireServiceImpl commentaireServiceImpl,
            PizzaCommandeServiceImpl pizzaCommandeServiceImpl,
            IngredientMapperImpl ingredientMapper,
            StandardMapperImpl standardMapper,
            PizzaMapperImpl pizzaMapper,
            CommandeMapperImpl commandeMapper,
            PizzaCommandeMapperImpl pizzaCommandeMapper,
            CommentaireMapperImpl commentaireMapper) {
        this.ingredientServiceImpl = ingredientServiceImpl;
        this.pizzaServiceImpl = pizzaServiceImpl;
        this.standardServiceImpl = standardServiceImpl;
        this.commandeServiceImpl = commandeServiceImpl;
        this.commentaireServiceImpl = commentaireServiceImpl;
        this.pizzaCommandeServiceImpl = pizzaCommandeServiceImpl;
        this.ingredientMapper = ingredientMapper;
        this.standardMapper = standardMapper;
        this.pizzaMapper = pizzaMapper;
        this.commandeMapper = commandeMapper;
        this.pizzaCommandeMapper = pizzaCommandeMapper;
        this.commentaireMapper = commentaireMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        // Create Ingredients
        IngredientDto ingredient1 = new IngredientDto();
        ingredient1.setName("Sauce tomate");
        ingredient1.setDescription("Description 1");
        ingredient1.setPhoto("path1.jpg");
        ingredient1.setPrix(10.0f);
        ingredient1 = ingredientServiceImpl.addIngredient(ingredient1);

        IngredientDto ingredient2 = new IngredientDto();
        ingredient2.setName("Crême fraiche");
        ingredient2.setDescription("Description 2");
        ingredient2.setPhoto("/pizza-1498148703.jpg");
        ingredient2.setPrix(20.0f);
        ingredient2 = ingredientServiceImpl.addIngredient(ingredient2);

        // Create Standards
        StandardDto standard1 = new StandardDto();
        standard1.setIngredients(Arrays.asList(ingredient1, ingredient2));


        StandardDto standard2 = new StandardDto();
        standard2.setIngredients(Arrays.asList(ingredient1));


        // Convert DTOs to Entities using Injected Mappers
        Ingredient ingredientEntity1 = ingredientMapper.toEntity(ingredient1);
        Ingredient ingredientEntity2 = ingredientMapper.toEntity(ingredient2);

        // Create Pizzas
        PizzaDto pizza1 = new PizzaDto();
        pizza1.setNom("Pizza Peperoni");
        pizza1.setDescription("Description 1");
        pizza1.setPhoto("/Pepperoni_Pizza_Beauty_1200x1200.webp");
        pizza1 = pizzaServiceImpl.addPizza(pizza1);

        standard1.setPizza(pizzaMapper.toEntity(pizza1));
        standard1 = standardServiceImpl.addStandard(standard1);

        PizzaDto pizza2 = new PizzaDto();
        pizza2.setNom("Pizza Margherita");
        pizza2.setDescription("Description 2");
        pizza2.setPhoto("/pizza-1498148703.jpg");
        pizza2 = pizzaServiceImpl.addPizza(pizza2);

        standard2.setPizza(pizzaMapper.toEntity(pizza2));
        standard2 = standardServiceImpl.addStandard(standard2);

        // Create Commandes
        Commande commande1 = new Commande();
        commande1.setDescription("Commande 1");
        commande1.setValidation(1);
        commande1.setDate("2023-10-01");
        commande1.setPrix(40.0f);
        CommandeDto commandeDto1 = commandeMapper.toDto(commande1);
        CommandeDto savedCommandeDto1 = commandeServiceImpl.addCommande(commandeDto1);

        // Create PizzaCommandes
        PizzaCommande pizzaCommande1 = new PizzaCommande();
        pizzaCommande1.setCommandeId(commandeMapper.toEntity(savedCommandeDto1).getId());
        pizzaCommande1.setPizza(pizzaMapper.toEntity(pizza1));
        pizzaCommande1.setIngredients(Arrays.asList(ingredientEntity1, ingredientEntity2));
        pizzaCommandeServiceImpl.createPizzaCommande(pizzaCommandeMapper.toDto(pizzaCommande1));

        // Create Commentaires
        Commentaire commentaire1 = new Commentaire();
        commentaire1.setDescription("Très bonne pizza !");
        commentaire1.setDate("2023-10-01");
        commentaire1.setPizzaOrigine(pizza1.getId());
        commentaire1.setNote(5);
        commentaireServiceImpl.addCommentaire(commentaireMapper.toDto(commentaire1));
    }
}
