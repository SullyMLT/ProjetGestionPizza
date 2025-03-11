package com.example.demo.repositories;

import com.example.demo.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class IngredientRepository extends JpaRepository<Ingredient,Integer> {
}
