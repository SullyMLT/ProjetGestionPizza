// src/components/IngredientList.js
import React, { useEffect, useState } from 'react';
const url = 'http://172.28.133.124:8080/ingredients';
const IngredientList = () => {
  const [ingredients, setIngredients] = useState([]);

  useEffect(() => {
    // Supposons que tu récupères la liste des ingrédients depuis une API
    // Pour l'instant, nous allons simplement simuler les données avec un tableau statique
    const fetchIngredients = async () => {
      try {
        // Remplace ceci par une requête API réelle si tu en as une
        const response = await fetch(url); // Assure-toi que ton endpoint existe
        const data = await response.json();
        setIngredients(data);
      } catch (error) {
        console.error('Erreur de récupération des ingrédients', error);
      }
    };

    fetchIngredients();
  }, []);

  return (
    <div>
      <h2>Liste des ingrédients</h2>
      <ul>
        {ingredients.length > 0 ? (
          ingredients.map((ingredient, index) => (
            <li key={index}>{ingredient.name}</li> // Affiche le nom de l'ingrédient
          ))
        ) : (
          <p>Aucun ingrédient trouvé.</p>
        )}
      </ul>
    </div>
  );
};

export default IngredientList;
