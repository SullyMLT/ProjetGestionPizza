import React, { useState } from 'react';

const PanierCommande = ({ panier, setPanier }) => {
  const [error, setError] = useState(null);

  // Fonction pour vider le panier
  const viderPanier = () => {
    setPanier([]);
    localStorage.removeItem('panier'); // Vider le panier dans localStorage
  };

  // Fonction pour supprimer un article spécifique du panier
  const supprimerDuPanier = (pizzaId) => {
    const updatedPanier = panier.filter(item => item.id !== pizzaId);
    setPanier(updatedPanier);
    localStorage.setItem('panier', JSON.stringify(updatedPanier)); // Met à jour localStorage
  };

  return (
    <div>
      {panier.length === 0 ? (
        <p>Votre panier est vide.</p>
      ) : (
        <div>
          <h3>Panier :</h3>
          <ul>
            {panier.map(pizza => (
              <li key={pizza.id}>
                <p>{pizza.nom} - {pizza.prix}€</p>
                <button onClick={() => supprimerDuPanier(pizza.id)}>Supprimer</button>
              </li>
            ))}
          </ul>
          <button onClick={viderPanier}>Vider le panier</button>
        </div>
      )}
      {error && <p className="error">{error}</p>}
    </div>
  );
};

export default PanierCommande;
