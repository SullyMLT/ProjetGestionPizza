package com.example.demo.repositories;

import com.example.demo.entities.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CommentaireRepository extends JpaRepository<Commentaire,Integer> {
}
