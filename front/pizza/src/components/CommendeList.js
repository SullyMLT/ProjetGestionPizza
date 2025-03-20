import React, { useState, useEffect } from 'react';
import "../App.css";

// URL de l'API
const url = 'http://localhost:8080/commandes';

const CommandeList = () => {

  const [commandes, setCommandes] = useState([]);  // État pour les commandes
  const [loading, setLoading] = useState(true);  // État pour savoir si les données sont en cours de chargement
  const [error, setError] = useState(null);  // État pour gérer les erreurs

  useEffect(() => {
    // Fonction pour récupérer les commandes depuis l'API
    const fetchCommandes = async () => {
      try {
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error('Erreur lors de la récupération des commandes');
        }
        const data = await response.json();
        setCommandes(data);  // Mettre à jour l'état des commandes avec les données récupérées
      } catch (error) {
        setError(error.message);  // Si une erreur survient, mettre à jour l'état d'erreur
      } finally {
        setLoading(false);  // Fin du chargement, mettre à jour l'état de chargement
      }
    };

    fetchCommandes();  // Appeler la fonction pour récupérer les commandes
  }, []);  // Le tableau vide [] signifie que l'effet se déclenche une seule fois, lors du montage du composant

  if (loading) {
    return <p>Chargement des commandes...</p>;  // Afficher un message de chargement tant que les données sont en cours de récupération
  }

  if (error) {
    return <p>Erreur: {error}</p>;  // Si une erreur survient, afficher un message d'erreur
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