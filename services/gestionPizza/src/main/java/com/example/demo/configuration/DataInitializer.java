package com.example.demo.configuration;

import com.example.demo.dtos.*;
import com.example.demo.entities.*;
import com.example.demo.mappers.*;
import com.example.demo.repositories.StatistiqueRepository;
import com.example.demo.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    private final CompteServiceImpl compteServiceImpl;
    private final CompteMapperImpl compteMapperImpl;
    private final StatistiqueRepository statistiqueRepository;

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
            CommentaireMapperImpl commentaireMapper, CompteServiceImpl compteServiceImpl,
            CompteMapperImpl compteMapperImpl,
            StatistiqueRepository statistiqueRepository) {
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
        this.compteServiceImpl = compteServiceImpl;
        this.compteMapperImpl = compteMapperImpl;
        this.statistiqueRepository = statistiqueRepository;
    }

    @Override
    public void run(String... args) throws Exception {



        Statistique statistique = new Statistique();
        statistique.setId("1");
        statistique.setStatPizza(new HashMap<>(0));
        statistique.setStatIngredient(new HashMap<>(0));
        this.statistiqueRepository.save(statistique);

        IngredientDto ingredient1 = new IngredientDto();
        ingredient1.setName("Sauce tomate");
        ingredient1.setDescription("Description 1");
        ingredient1.setPhoto("/sacue-tomate-basilic.webp");
        ingredient1.setPrix(10.0f);
        ingredient1 = ingredientServiceImpl.addIngredient(ingredient1);

        IngredientDto ingredient2 = new IngredientDto();
        ingredient2.setName("Crême fraiche");
        ingredient2.setDescription("Description 2");
        ingredient2.setPhoto("/creme-fraiche.jpeg");
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
        pizza1.setActiver(true);
        pizza1 = pizzaServiceImpl.addPizza(pizza1);

        standard1.setPizza(pizza1);
        standard1 = standardServiceImpl.addStandard(standard1);

        PizzaDto pizza2 = new PizzaDto();
        pizza2.setNom("Pizza Margherita");
        pizza2.setDescription("Description 2");
        pizza2.setPhoto("/pizza-1498148703.jpg");
        pizza2.setActiver(true);
        pizza2 = pizzaServiceImpl.addPizza(pizza2);

        standard2.setPizza(pizza2);
        standard2 = standardServiceImpl.addStandard(standard2);

        // Compte

        Compte compte1 = new Compte();
        compte1.setUsername("admin");
        compte1.setPassword("$2b$10$EXzevRgyKWKLM0.WZNQ.ru6bgPNqF4U9DTUdgsBB19AkFMyZeIYvW");
        compte1.setRole("admin");
        compte1.setActiver(true);
        CompteDto savedCompteDto1 = compteServiceImpl.createCompte(compteMapperImpl.toDto(compte1));

        Compte compteUserDelete = new Compte();
        compteUserDelete.setUsername("UtilisateurSupprimé");
        compteUserDelete.setPassword("userDelete");
        compteUserDelete.setRole("admin");
        compteUserDelete.setActiver(true);
        CompteDto savedCompteDtoUserDelete = compteServiceImpl.createCompte(compteMapperImpl.toDto(compteUserDelete));


        // Create Commandes
        Commande commande1 = new Commande();
        commande1.setDescription("Commande 1");
        commande1.setValidation(false);
        commande1.setDate("2023-10-01");
        commande1.setPrix(0);
        commande1.setCompteId(1L);
        CommandeDto commandeDto1 = commandeMapper.toDto(commande1);
        CommandeDto savedCommandeDto1 = commandeServiceImpl.addCommande(commandeDto1);

        // Create PizzaCommandes
        PizzaCommande pizzaCommande1 = new PizzaCommande();
        pizzaCommande1.setCommandeId(commandeMapper.toEntity(savedCommandeDto1).getId());
        System.out.println("CommandeId: "+ commandeMapper.toEntity(savedCommandeDto1).getId());
        pizzaCommande1.setPizza(pizzaMapper.toEntity(pizza1));
        pizzaCommande1.setIngredients(Arrays.asList(ingredientEntity1, ingredientEntity2));
        pizzaCommandeServiceImpl.createPizzaCommande(pizzaCommandeMapper.toDto(pizzaCommande1));

        // Create Commentaires
        Commentaire commentaire1 = new Commentaire();
        commentaire1.setDescription("Très bonne pizza !");
        commentaire1.setDate("2025-03-21");
        commentaire1.setPizzaOrigine(pizza1.getId());
        commentaire1.setNote(5);
        commentaire1.setCompteId(savedCompteDto1.getId());
        commentaireServiceImpl.addCommentaire(commentaireMapper.toDto(commentaire1));



    }

}
