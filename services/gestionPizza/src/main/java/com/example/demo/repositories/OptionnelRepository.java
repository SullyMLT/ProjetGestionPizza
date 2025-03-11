package com.example.demo.repositories;

import com.example.demo.entities.Optionnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionnelRepository extends JpaRepository<Optionnel,Integer> {
}
