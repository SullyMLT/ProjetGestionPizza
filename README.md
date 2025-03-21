# ProjetPizza

## Description

ProjetPizza est une application web permettant aux utilisateurs de consulter une liste de pizzas, de passer des commandes et de gérer leur panier. Les administrateurs peuvent ajouter des pizzas, gérer les ingrédients et consulter les commandes.

## Technologies utilisées

Frontend : React, React Router

Backend : Express.js, Node.js, Spring Boot

Base de données : (préciser, ex: MongoDB, MySQL...)

Autres : Axios pour les requêtes HTTP, JWT pour l'authentification

## Prérequis

Node.js installé

npm install (bcrypt,multer,react-router-dom,chart)

## Fonctionnalités :

Utilisateurs :

S'inscrire et se connecter

Consulter la liste des pizzas

Ajouter des pizzas au panier

Passer des commandes

Consulter leurs commandes

Administrateurs :

Ajouter des pizzas

Ajouter et gérer les ingrédients

Consulter la liste des commandes

Consulter les statistiques des pizzas

## Routes principales

Public

/ : Liste des pizzas

/login : Connexion

/register : Inscription

Utilisateur connecté

/panier : Panier

/commande : Commandes utilisateur

/pizzas/:id : Détails d'une pizza

/statistique-pizza : Statistiques

## Administrateur

/ajouter-pizza : Ajouter une pizza

/ajouter-ingredient : Ajouter un ingrédient

/ingredients : Liste des ingrédients

/commendes : Liste des commandes

## Auteur

Sully MILLET

Christian Esteban NUNEZ GUAJARDO