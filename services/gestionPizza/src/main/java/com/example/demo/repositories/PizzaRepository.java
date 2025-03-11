package com.example.demo.repositories;

import com.example.demo.entities.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PizzaRepository extends JpaRepository<Pizza,Integer> {}
