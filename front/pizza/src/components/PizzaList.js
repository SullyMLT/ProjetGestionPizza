import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import "./css/PizzaList.css";

import { url_host } from '../config/config.js';

const url = url_host +'/pizzas';

const token = localStorage.getItem('token');

const PizzaList = () => {
  const [pizzas, setPizzas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {

    const fetchPizzas = async () => {
      try {
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error('Erreur lors de la récupération des pizzas');
        }
        const data = await response.json();
        setPizzas(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchPizzas();
  },[]);

  if (loading) {
    return <p>Chargement des pizzas...</p>;
  }

  if (error) {
    return <p>Erreur: {error}</p>;
  }

  const handleDetailClick = (pizzaId) => {

    navigate(`/pizzas/${pizzaId}`);
  };

  return (
    <section>
      <h2>Liste des Pizzas</h2>
      <div className="pizza-container">
        {pizzas.map(pizza => (
          <div key={pizza.id} className="pizza-tile">
            <div className="left">
              <h3>{pizza.nom}</h3>
              <p>{pizza.description}</p>
              <img src={pizza.photo} alt={pizza.nom} />
            </div>
            <div className="right">
              <p><strong>Prix: {pizza.prix}€</strong></p>
              {token ? (
              <button className="add-to-cart" onClick={() => handleDetailClick(pizza.id)}>
                Détail
              </button>) : (<></>)}
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default PizzaList;
