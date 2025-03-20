import React, { useState } from "react";
import "./css/AddIngredient.css";

import { url_host } from '../config/config.js';

const url = url_host;

function AddIngredient({ addIngredient }) {
  const [ingredient, setIngredient] = useState({
    name: "",
    description: "",
    pathPhoto: "",
    prix: "",
  });

  // Etat pour la prévisualisation de l'image
  const [imagePreview, setImagePreview] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setIngredient((prevIngredient) => ({ ...prevIngredient, [name]: value }));
  };

  // Fonction pour gérer le changement de fichier (image)
  const handleFileChange = (e) => {
    const file = e.target.files[0]; // Récupère le premier fichier sélectionné
    if (file) {
      const reader = new FileReader();

      reader.onloadend = () => {
        // Prévisualisation de l'image
        setImagePreview(reader.result);

        // Générer un chemin relatif pour l'image (mettre à jour l'état)
        const imagePath = `/images/ingredients/${file.name}`; // Exemple de chemin pour les images dans un dossier 'public/images/ingredients'
        setIngredient((prevIngredient) => ({
          ...prevIngredient,
          pathPhoto: imagePath, // Met à jour le chemin de l'image
        }));
      };

      reader.onerror = (error) => {
        console.error('Erreur lors de la lecture du fichier image:', error);
        alert("Une erreur est survenue lors de la lecture de l'image.");
      };

      reader.readAsDataURL(file); // Lire l'image en base64 pour la prévisualisation
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Vérifier que tous les champs sont remplis
    if (Object.values(ingredient).includes("") || !ingredient.pathPhoto) {
      return alert("Tous les champs doivent être remplis, y compris l'image.");
    }

    try {
      const response = await fetch(url + "/ingredients", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(ingredient),
      });

      if (response.ok) {
        setIngredient({ name: "", description: "", pathPhoto: "", prix: "" });
        setImagePreview(""); // Réinitialiser la prévisualisation de l'image
        alert("Ingrédient ajouté avec succès !");
      } else {
        throw new Error("Erreur lors de l'ajout de l'ingrédient");
      }
    } catch (error) {
      console.error(error);
      alert(error.message || "Erreur lors de l'ajout de l'ingrédient");
    }
  };

  return (
    <div className="add-ingredient-form">
      <h2>Ajouter un Ingrédient</h2>
      <form onSubmit={handleSubmit} className="ingredient-form">

        {/* Section pour le name de l'ingrédient */}
        <div className="form-section">
          <div className="form-group">
            <label htmlFor="name">Nom</label>
            <input
              type="text"
              id="name"
              name="name"
              value={ingredient.name}
              onChange={handleChange}
              placeholder="Entrez le nom de l'ingrédient"
              required
            />
          </div>
        </div>

        {/* Section pour la description */}
        <div className="form-section">
          <div className="form-group">
            <label htmlFor="description">Description</label>
            <input
              type="text"
              id="description"
              name="description"
              value={ingredient.description}
              onChange={handleChange}
              placeholder="Entrez une description"
              required
            />
          </div>
        </div>

        {/* Section pour la pathPhoto (image) */}
        <div className="form-section">
          <div className="form-group">
            <label htmlFor="pathPhoto">Photo de l'Ingrédient</label>
            <input
              type="file"
              id="pathPhoto"
              name="pathPhoto"
              onChange={handleFileChange}
              accept="image/*"
              required
            />
          </div>
        </div>

        {/* Afficher la prévisualisation de l'image */}
        {imagePreview && (
          <div className="image-preview">
            <img
              src={imagePreview}
              alt="Prévisualisation"
              style={{
                maxWidth: '100%',
                maxHeight: '300px',
                objectFit: 'contain',
              }}
            />
          </div>
        )}

        {/* Section pour le prix */}
        <div className="form-section">
          <div className="form-group">
            <label htmlFor="prix">Prix</label>
            <input
              type="number"
              id="prix"
              name="prix"
              value={ingredient.prix}
              onChange={handleChange}
              placeholder="Entrez le prix de l'ingrédient"
              required
            />
          </div>
        </div>

        {/* Bouton de soumission */}
        <button type="submit" className="button">
          Ajouter un Ingrédient
        </button>
      </form>
    </div>
  );
}

export default AddIngredient;
