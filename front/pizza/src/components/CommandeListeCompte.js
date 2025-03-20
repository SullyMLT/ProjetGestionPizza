import React, { useEffect, useState } from 'react';

import { url_host } from '../config/config.js';

const url = url_host;

const CommandesList = ({ compteId }) => {
  const [commandes, setCommandes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCommandes = async () => {
      try {
        const response = await fetch(url+`/commandes/commande-compte/${compteId}`);
        if (!response.ok) {
          throw new Error('Erreur lors de la récupération des commandes');
        }
        const data = await response.json();
        setCommandes(data);
        setLoading(false);
      } catch (error) {
        setError(error.message);
        setLoading(false);
      }
    };

    if (compteId) {
      fetchCommandes();
    }

  }, [compteId]);

  if (loading) return <p>Chargement des commandes...</p>;
  if (error) return <p>Erreur: {error}</p>;

  return (
    <div className="commandes-list">
      <h3>Liste des Commandes</h3>
      {commandes.length === 0 ? (
        <p>Aucune commande trouvée pour ce compte.</p>
      ) : (
        <ul>
          {commandes.map((commande) => (
            <li key={commande.id}>
              <h4>Commande ID: {commande.id}</h4>
              <p>Description: {commande.description}</p>
              <p>Date: {commande.date}</p>
              <p>Prix: {commande.prix}€</p>
              <p>Validation: {commande.validation ? 'Validée' : 'Non validée'}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default CommandesList;
