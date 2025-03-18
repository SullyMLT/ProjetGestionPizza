package com.example.demo.repositories;

import com.example.demo.entities.Statistique;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatistiqueRepository extends MongoRepository<Statistique, String> {
}