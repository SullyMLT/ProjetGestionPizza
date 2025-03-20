import React, { useState, useEffect } from 'react';
import "../App.css";

// URL de l'API
const url = 'http://localhost:8080/commandes';

const CommandeList = () => {

  const [commandes, setCommandes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Fonction pour récupérer les commandes depuis l'API
    const fetchCommandes = async () => {
      try {
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error('Erreur lors de la récupération des commandes');
        }
        const data = await response.json();
        setCommandes(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCommandes();
  }, []);

  if (loading) {
    return <p>Chargement des commandes...</p>;
  }

  if (error) {
    return <p>Erreur: {error}</p>;
  }

  return (
    <section>
      <h2>Liste des Commandes</h2>
      <div className="commande-container">
        {commandes.map(commande => (
          <div key={commande.id} className="commande-tile">
            <div className="left">


              <p><strong>Date: </strong>{new Date(commande.date).toLocaleDateString()}</p>
            </div>
            <div className="right">
              <p><strong>Prix: </strong>{commande.prix.toFixed(2)} €</p>  {/* Format prix avec deux décimales */}
              <p><strong>Validation: </strong>{commande.validation ? 'Validée' : 'Non validée'}</p>
              <p><strong>Date complète: </strong>{new Date(commande.date).toLocaleString()}</p>  {/* Date au format lisible */}
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default CommandeList;