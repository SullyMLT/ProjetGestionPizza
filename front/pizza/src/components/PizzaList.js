import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';  // Importer useNavigate
import "../App.css";

// URL de l'API
const url = 'http://172.28.133.124:8080/pizzas';

const PizzaList = () => {
  const [pizzas, setPizzas] = useState([]);  // État pour les pizzas
  const [loading, setLoading] = useState(true);  // État pour savoir si les données sont en cours de chargement
  const [error, setError] = useState(null);  // État pour gérer les erreurs
  const navigate = useNavigate();  // Hook pour la navigation

  useEffect(() => {
    // Fonction pour récupérer les pizzas depuis l'API
    const fetchPizzas = async () => {
      try {
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error('Erreur lors de la récupération des pizzas');
        }
        const data = await response.json();
        setPizzas(data);  // Mettre à jour l'état des pizzas avec les données récupérées
      } catch (error) {
        setError(error.message);  // Si une erreur survient, mettre à jour l'état d'erreur
      } finally {
        setLoading(false);  // Fin du chargement, mettre à jour l'état de chargement
      }
    };

    fetchPizzas();  // Appeler la fonction pour récupérer les pizzas
  }, []);  // Le tableau vide [] signifie que l'effet se déclenche une seule fois, lors du montage du composant

  if (loading) {
    return <p>Chargement des pizzas...</p>;  // Afficher un message de chargement tant que les données sont en cours de récupération
  }

  if (error) {
    return <p>Erreur: {error}</p>;  // Si une erreur survient, afficher un message d'erreur
  }

  const handleDetailClick = (pizzaId) => {
    // Rediriger vers la page de détails de la pizza
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
              <button className="add-to-cart" onClick={() => handleDetailClick(pizza.id)}>
                Détail
              </button>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default PizzaList;
