import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom"; // Importer useParams

const PizzaDetails = ({ panier, setPanier }) => {
  const { id } = useParams(); // Récupérer l'ID de la pizza depuis l'URL
  const [pizza, setPizza] = useState(null);
  const [ingredients, setIngredients] = useState([]); // Tous les ingrédients disponibles
  const [standardIngredients, setStandardIngredients] = useState([]); // Ingrédients standards pour cette pizza
  const [selectedIngredients, setSelectedIngredients] = useState([]); // Ingrédients sélectionnés par l'utilisateur
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Récupérer les détails de la pizza
    const fetchPizzaDetails = async () => {
      try {
        const pizzaResponse = await fetch(`http://172.28.133.124:8080/pizzas/${id}`);
        if (!pizzaResponse.ok) {
          throw new Error('Erreur lors de la récupération des détails de la pizza');
        }
        const pizzaData = await pizzaResponse.json();
        setPizza(pizzaData); // Mettre à jour les détails de la pizza

        // Récupérer les ingrédients standards associés à cette pizza
        const standardResponse = await fetch(`http://172.28.133.124:8080/standards/${pizzaData.standardId}`);
        if (!standardResponse.ok) {
          throw new Error('Erreur lors de la récupération des ingrédients standards');
        }
        const standardData = await standardResponse.json();
        setStandardIngredients(standardData.ingredients); // Ingrédients standards

      } catch (error) {
        setError(error.message); // Gérer l'erreur
      } finally {
        setLoading(false); // Fin du chargement
      }
    };

    // Récupérer tous les ingrédients disponibles
    const fetchAllIngredients = async () => {
      try {
        const ingredientsResponse = await fetch('http://172.28.133.124:8080/ingredients');
        if (!ingredientsResponse.ok) {
          throw new Error('Erreur lors de la récupération de tous les ingrédients');
        }
        const allIngredientsData = await ingredientsResponse.json();
        setIngredients(allIngredientsData); // Mettre à jour la liste d'ingrédients
      } catch (error) {
        setError(error.message); // Gérer l'erreur
      }
    };

    fetchPizzaDetails(); // Appeler la fonction pour récupérer les détails de la pizza
    fetchAllIngredients(); // Appeler la fonction pour récupérer tous les ingrédients
  }, [id]); // Le composant se met à jour avec un nouvel ID de pizza

  // Fonction pour gérer le changement d'état des ingrédients
  const handleCheckboxChange = (ingredientId, checked) => {
    if (checked) {
      setSelectedIngredients((prevSelected) => [...prevSelected, ingredientId]);
    } else {
      setSelectedIngredients((prevSelected) =>
        prevSelected.filter((id) => id !== ingredientId)
      );
    }
  };

  // Fonction pour ajouter la pizza au panier
  const handleAddToCart = () => {
    if (!pizza) return;

    // Vérification du panier dans le localStorage
    let currentPanier = JSON.parse(localStorage.getItem("panier")) || [];

    // Si le panier est vide, on crée une nouvelle commande
    if (currentPanier.length === 0) {
      const newCommande = {
        description: 'Commande en cours',
        validation: 0,
        date: new Date().toISOString(), // Date de la commande
        prix: pizza.prix, // Prix de la pizza (peut-être ajouté à la commande après)
        pizzasCommandes: [], // Liste des pizzas de cette commande
      };
      currentPanier.push(newCommande); // Ajouter la commande vide au panier
    }

    // Créer une pizza personnalisée avec les ingrédients sélectionnés
    const pizzaCommande = {
      commandeId: currentPanier[0].id, // Assigner la pizza à la première commande (si on n'a qu'une seule commande)
      pizzaDto: pizza,
      ingredients: selectedIngredients,
    };

    // Ajouter la pizza personnalisée à la commande existante
    currentPanier[0].pizzasCommandes.push(pizzaCommande);

    // Mettre à jour le panier dans le localStorage
    localStorage.setItem("panier", JSON.stringify(currentPanier));
    setPanier(currentPanier); // Mettre à jour l'état du panier
  };

  if (loading) {
    return <p>Chargement des détails...</p>;
  }

  if (error) {
    return <p>Erreur: {error}</p>;
  }

  if (!pizza) {
    return null;
  }

  return (
    <div className="pizza-details">
      <h3>Détails de la pizza : {pizza.nom}</h3>
      <p>{pizza.description}</p>
      <img src={pizza.photo} alt={pizza.nom} width="300" />
      <h4>Prix : {pizza.prix}€</h4>

      <h4>Ingrédients :</h4>
      <ul>
        {ingredients.map((ingredient) => {
          const isStandard = standardIngredients.some(
            (standardIngredient) => standardIngredient.id === ingredient.id
          );
          const isChecked = selectedIngredients.includes(ingredient.id) || isStandard;

          return (
            <li key={ingredient.id}>
              <label>
                <input
                  type="checkbox"
                  checked={isChecked} // Si c'est un ingrédient standard, coché par défaut
                  onChange={(e) => handleCheckboxChange(ingredient.id, e.target.checked)}
                />
                {ingredient.name}
              </label>
            </li>
          );
        })}
      </ul>

      <button onClick={handleAddToCart}>Ajouter au panier</button>
    </div>
  );
};

export default PizzaDetails;
