import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import axios from "axios";

import CommentairePizza from "./CommentairePizza";
import { url_host } from '../config/config.js';

const url = url_host;

const PizzaDetails = ({ userID }) => {
  const { id } = useParams();
  const [pizza, setPizza] = useState(null);
  const [ingredients, setIngredients] = useState([]);
  const [selectedIngredients, setSelectedIngredients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
 const navigate = useNavigate();


  // Récupérer les détails de la pizza et les ingrédients
  useEffect(() => {
    const fetchPizzaDetails = async () => {
      try {
        const pizzaResponse = await axios.get(`${url}/pizzas/${id}`);
        setPizza(pizzaResponse.data);

        // Récupérer les ingrédients standards associés à cette pizza
        const standardResponse = await axios.get(`${url}/standards/pizza/${id}`);

        setSelectedIngredients(standardResponse.data.ingredients);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    // Récupérer tous les ingrédients disponibles
    const fetchAllIngredients = async () => {
      try {
        const ingredientsResponse = await axios.get(`${url}/ingredients`);
        setIngredients(ingredientsResponse.data);
      } catch (error) {
        setError(error.message);
      }
    };

    fetchPizzaDetails();
    fetchAllIngredients();
  }, [id]);

  // Fonction pour gérer le changement d'état des ingrédients
  const handleCheckboxChange = (ingredient, checked) => {
    setSelectedIngredients(prevSelected =>
      checked
        ? [...prevSelected, ingredient]
        : prevSelected.filter(i => i.id !== ingredient.id)
    );
  };

  // Ajouter la pizza à la commande
  const handleAddToCart = async () => {
    if (!pizza) return;

    const newCommande = {
      description: "Commande en cours",
      validation: false,
      date: new Date().toISOString(),
      prix: '0',
      compteId : userID
    };

    try {
      const createCommandeResponse = await axios.post(`${url}/commandes`, newCommande, {
        params: { compteId: userID },
      });
      const commandeId = createCommandeResponse.data.id;
       console.log(commandeId +" id")
      const pizzaCommande = {
        commandeId,
        pizza,
        ingredients: selectedIngredients,
      };

      const addPizzaResponse = await axios.post(`${url}/pizzaCommandes`, pizzaCommande);

      if (!addPizzaResponse.data) {
        throw new Error('Erreur lors de l\'ajout de la pizza à la commande');
      }

      alert('Pizza ajoutée à la commande !');
      navigate('/');
      window.location.reload();
    } catch (error) {
      alert('Erreur lors de l\'ajout au panier');
    }
  };

  if (loading) {
    return <p>Chargement des détails...</p>;
  }

  if (error) {
    return <p>Une erreur est survenue : {error}</p>;
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

          const isChecked = selectedIngredients.some(i => i.id === ingredient.id);

          return (
            <li key={ingredient.id}>
              <label>
                <input
                  type="checkbox"
                  checked={isChecked} // Si c'est un ingrédient standard, coché par défaut
                  onChange={(e) => handleCheckboxChange(ingredient, e.target.checked)}
                />
                {ingredient.name} -
                {ingredient.prix}€
              </label>
            </li>
          );
        })}
      </ul>

      <button onClick={handleAddToCart}>Ajouter au panier</button>

      <CommentairePizza pizzaId={id} />
    </div>
  );
};

export default PizzaDetails;
