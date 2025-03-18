import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

const PizzaDetails = ({ commande, setCommande }) => {
  const { id } = useParams(); // Récupérer l'ID de la pizza depuis l'URL
  const [pizza, setPizza] = useState(null); // Détails de la pizza
  const [ingredients, setIngredients] = useState([]); // Liste d'ingrédients
  const [standardIngredients, setStandardIngredients] = useState([]); // Ingrédients standards pour la pizza
  const [selectedIngredients, setSelectedIngredients] = useState([]); // Ingrédients sélectionnés par l'utilisateur
  const [loading, setLoading] = useState(true); // État de chargement
  const [error, setError] = useState(null); // Gestion des erreurs

  const token = localStorage.getItem('token'); // Récupère le token du localStorage

  // Récupérer les détails de la pizza et les ingrédients
  useEffect(() => {
    const fetchPizzaDetails = async () => {
      try {
        const pizzaResponse = await fetch(`http://172.28.133.124:8080/pizzas/${id}`);
        if (!pizzaResponse.ok) {
          throw new Error('Erreur lors de la récupération des détails de la pizza');
        }
        const pizzaData = await pizzaResponse.json();
        setPizza(pizzaData); // Mettre à jour les détails de la pizza

        // Récupérer les ingrédients standards associés à cette pizza
        const standardResponse = await fetch(`http://172.28.133.124:8080/standards/pizza/${id}`);
        if (!standardResponse.ok) {
          throw new Error('Erreur lors de la récupération des ingrédients standards');
        }
        const standardData = await standardResponse.json();
        setStandardIngredients(standardData.ingredients); // Mettre à jour les ingrédients standards
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    // Récupérer tous les ingrédients disponibles
    const fetchAllIngredients = async () => {
      try {
        const ingredientsResponse = await fetch('http://172.28.133.124:8080/ingredients');
        if (!ingredientsResponse.ok) {
          throw new Error('Erreur lors de la récupération de tous les ingrédients');
        }
        const allIngredientsData = await ingredientsResponse.json();
        setIngredients(allIngredientsData);
      } catch (error) {
        setError(error.message);
      }
    };

    fetchPizzaDetails();
    fetchAllIngredients();
  }, [id]);

  // Fonction pour gérer le changement d'état des ingrédients
  const handleCheckboxChange = (ingredientId, checked) => {
    if (checked) {
      setSelectedIngredients(prevSelected => [...prevSelected, ingredientId]);
    } else {
      setSelectedIngredients(prevSelected => prevSelected.filter(id => id !== ingredientId));
    }
  };

  // Ajouter la pizza à la commande
  const handleAddToCart = async () => {
    if (!pizza) return;

    // Si aucune commande n'existe, en créer une nouvelle
    let commandeId = commande ? commande.id : null;

    if (!commandeId) {
      const newCommande = {
        description: "Commande en cours",
        validation: 0,
        date: new Date().toISOString(),
        prix: 0, // Prix par défaut, tu peux le mettre à jour plus tard
        pizzaDto: null, // Pas de pizza au début
        ingredients: [], // Pas d'ingrédients au début
      };

      try {
        // Créer la commande sur le serveur
        const createCommandeResponse = await axios.post('http://172.28.133.124:8080/commandes', newCommande);

        // Assigner l'ID de la commande à l'objet commande
        commandeId = createCommandeResponse.data.id;
        setCommande(createCommandeResponse.data); // Mettre à jour l'état de la commande
      } catch (error) {
        console.error("Erreur lors de la création de la commande", error);
        return;
      }
    }

    // Ajouter la pizza à la commande existante
    const pizzaCommande = {
      commandeId,
      pizzaDto: pizza,
      ingredients: selectedIngredients,
    };

    try {
      const addPizzaResponse = await fetch('http://172.28.133.124:8080/pizzaCommandes', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(pizzaCommande),
      });

      if (!addPizzaResponse.ok) {
        throw new Error('Erreur lors de l\'ajout de la pizza à la commande');
      }

      alert('Pizza ajoutée à la commande !');
    } catch (error) {
      alert('Erreur lors de l\'ajout au panier');
    }
  };

  if (loading) {
    return <p>Chargement des détails...</p>;
  }

  if (error) {
    return <p>{error}</p>;
  }

  if (!pizza) {
    return null;
  }

  return (

