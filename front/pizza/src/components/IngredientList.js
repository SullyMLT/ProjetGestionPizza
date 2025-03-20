import React, { useEffect, useState } from 'react';
import { url_host } from '../config/config.js';

import "./css/IngredientList.css";

const url = url_host + '/ingredients';

const IngredientList = () => {
  const [ingredients, setIngredients] = useState([]);

  useEffect(() => {
    // Supposons que tu récupères la liste des ingrédients depuis une API
    const fetchIngredients = async () => {
      try {
        const response = await fetch(url);
        const data = await response.json();
        setIngredients(data);
      } catch (error) {
        console.error('Erreur de récupération des ingrédients', error);
      }
    };

    fetchIngredients();
  }, []);

  return (
    <div className="ingredient-list">
      <h2 className="ingredient-list-title">Liste des ingrédients</h2>
      <ul className="ingredient-list-items">
        {ingredients.length > 0 ? (
          ingredients.map((ingredient, index) => (
            <li key={index} className="ingredient-item">
              <span className="ingredient-name">{ingredient.name}</span>
            </li>
          ))
        ) : (
          <p className="no-ingredients-message">Aucun ingrédient trouvé.</p>
        )}
      </ul>
    </div>
  );
};

export default IngredientList;
