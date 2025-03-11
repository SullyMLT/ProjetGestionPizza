package com.example.demo.repositories;

import com.example.demo.entities.PizzaCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaCommandeRepository extends JpaRepository<PizzaCommande, Long> {
}
