import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

const PizzaDetails = ({ userID }) => {
  const { id } = useParams(); // Récupérer l'ID de la pizza depuis l'URL
  const [pizza, setPizza] = useState(null); // Détails de la pizza
  const [ingredients, setIngredients] = useState([]); // Liste d'ingrédients
  const [standardIngredients, setStandardIngredients] = useState([]); // Ingrédients standards pour la pizza
  const [selectedIngredients, setSelectedIngredients] = useState([]); // Ingrédients sélectionnés par l'utilisateur (objets complets)
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
  const handleCheckboxChange = (ingredient, checked) => {
    if (checked) {
      // Ajouter l'objet complet de l'ingrédient dans selectedIngredients
      setSelectedIngredients(prevSelected => [...prevSelected, ingredient]);
    } else {
      // Retirer l'objet complet de l'ingrédient de selectedIngredients
      setSelectedIngredients(prevSelected => prevSelected.filter(i => i.id !== ingredient.id));
    }
    console.log(selectedIngredients);
  };

  // Ajouter la pizza à la commande
  const handleAddToCart = async () => {
    if (!pizza) return;
    console.log('Create commande');
    const newCommande = {
      description: "Commande en cours",
      validation: false,
      date: new Date().toISOString(),
      prix: 0
    };

    try {
      // Créer la commande sur le serveur
      const createCommandeResponse = await axios.post('http://172.28.133.124:8080/commandes', newCommande,{params : {compteId : userID}});
      const commandeId = createCommandeResponse.data.id;

      // Ajouter la pizza à la commande existante
      const pizzaCommande = {
        commandeId : commandeId,
        pizza: pizza,
        ingredients: selectedIngredients,
      };
      console.log(pizzaCommande);

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
    } catch (error) {
      console.error("Erreur lors de la création de la commande", error);
      return;
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
    <div className="pizza-details">
      <h3>Détails de la pizza : {pizza.nom}</h3>
      <p>{pizza.description}</p>
      <img src={pizza.photo} alt={pizza.nom} width="300" />
      <h4>Prix : {pizza.prix}€</h4>

      <h4>Ingrédients :</h4>
      <ul>
        {ingredients.map((ingredient) => {
          const isStandard = standardIngredients.some(
            (standardIngredient) => standardIngredient.id === ingredient.id
          );
          const isChecked = selectedIngredients.some(i => i.id === ingredient.id) || isStandard;

          return (
            <li key={ingredient.id}>
              <label>
                <input
                  type="checkbox"
                  checked={isChecked} // Si c'est un ingrédient standard, coché par défaut
                  onChange={(e) => handleCheckboxChange(ingredient, e.target.checked)}
                />
                {ingredient.name}
              </label>
            </li>
          );
        })}
      </ul>

      <button onClick={handleAddToCart}>Ajouter au panier</button>
    </div>
  );
};

export default PizzaDetails;
