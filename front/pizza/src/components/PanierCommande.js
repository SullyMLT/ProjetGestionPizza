import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { url_host } from '../config/config.js';
const url = url_host;

const PanierCommande = ({ userID }) => {
  const [error, setError] = useState(null);
  const [commandePizzas, setCommandePizzas] = useState([]);
  const [commande, setCommande] = useState(null);

  useEffect(() => {
    const createCommande = async () => {
      const newCommande = {
        description: "Commande en cours",
        validation: false,
        date: new Date().toISOString(),
        prix: 0,
        compteId : userID
      };

      try {
        // Créer la commande sur le serveur
        const createCommandeResponse = await axios.post(url + '/commandes', newCommande, { params: { compteId: userID } });
        const createdCommande = createCommandeResponse.data;

        // Mettre à jour l'état avec la commande créée
        setCommande(createdCommande);
         console.log(userID)
        // Récupérer les pizzas de la commande après la création de la commande
        const commandePizzasResponse = await axios.get(url + `/pizzaCommandes/commande/${createdCommande.id}`);
        setCommandePizzas(commandePizzasResponse.data || []);

      } catch (error) {
        console.error("Erreur lors de la création de la commande", error);
        setError("E");
      }
    };

    // Appeler la fonction pour créer la commande lorsque le userID change
    if (userID) {
      createCommande();
    }
  }, [userID]); // Reexécuter seulement si userID change

// Fonction pour supprimer une pizza de la commande (via PizzaCommande)
const deletePizza = async (pizzaCommandeId) => {
  try {

    await axios.delete(url + `/pizzaCommandes/${pizzaCommandeId}`);

    // Mettre à jour l'état en retirant la pizza de la commande
    setCommandePizzas(commandePizzas.filter(pizzaCommande => pizzaCommande.id !== pizzaCommandeId));
  } catch (error) {
    console.error("Erreur lors de la suppression de la pizza de la commande", error);
    setError("Erreur lors de la suppression de la pizza de la commande");
  }
};

  // Fonction pour valider la commande
  const validateCommande = async () => {
    if (!commande) {
      console.error("Commande ID is missing");
      setError("Commande ID is missing");
      return;
    }

    try {
      await axios.put(url + `/commandes/validation/${commande.id}`);
      alert('Commande validée avec succès');
    } catch (error) {
      console.error("Erreur lors de la validation de la commande", error);
      setError("Erreur lors de la validation de la commande");
    }
  };

  return (
    <div>
      {error && <p>{error}</p>}

      <h2>Liste des Pizzas de la Commande</h2>
      <ul>
        {commandePizzas.map(pizzaCommande => (
          <li key={pizzaCommande.id}>
            <div>
              <img src={pizzaCommande.pizza.photo} alt={pizzaCommande.pizza.nom} width="100" />
              <span>{pizzaCommande.pizza.nom}</span> - {pizzaCommande.pizza.prix}€
            </div>
            <div>
              <p><strong>Description:</strong> {pizzaCommande.pizza.description}</p>
              <p><strong>Ingrédients:</strong></p>
              <ul>
                {pizzaCommande.ingredients.map(ingredient => (
                  <li key={ingredient.id}>
                    {ingredient.name} - {ingredient.prix}€
                  </li>
                ))}
              </ul>

            </div>

            <button onClick={() => deletePizza(pizzaCommande.id)}>Supprimer</button>
          </li>
        ))}
      </ul>

      <div>

        {/* "Valider" button pour confirmer la commande */}
         <h2>Prix total = {commande ? commande.prix : '...' } €</h2>
        <button onClick={validateCommande} disabled={!commande}>Valider la commande</button>
      </div>
    </div>
  );
};

export default PanierCommande;
