package com.example.demo.repositories;

import com.example.demo.entities.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StandardRepository extends JpaRepository<Standard, Long> {
}
