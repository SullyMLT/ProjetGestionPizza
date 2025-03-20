import React, { useEffect, useState } from 'react';
import { url_host } from '../config/config.js';

import "./css/IngredientList.css";

const url = url_host + '/ingredients';

const IngredientList = () => {
  const [ingredients, setIngredients] = useState([]);

  useEffect(() => {
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
              <p className="ingredient-description">{ingredient.description}</p>
              {/* Afficher l'image de l'ingrédient */}
              {ingredient.photo && (
                <img
                  src={ingredient.photo}
                  alt={ingredient.name}
                  className="ingredient-photo"
                  style={{ maxWidth: '100px', maxHeight: '100px', objectFit: 'contain' }}
                />
              )}
              <p className="ingredient-price">Prix : {ingredient.prix}€</p>
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
