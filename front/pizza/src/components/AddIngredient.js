import React, { useState } from "react";
import "../App.css";
import { url_host } from '../config/config.js';

const url = url_host;

function AddIngredient({ addIngredient }) {
  const [ingredient, setIngredient] = useState({
    name: "",
    description: "",
    photo: "",
    prix: "",
  });

  // Etat pour la prévisualisation de l'image
  const [imagePreview, setImagePreview] = useState("");
  const [file, setFile] = useState(null);  // Pour stocker le fichier image sélectionné

  const handleChange = (e) => {
    const { name, value } = e.target;
    setIngredient((prevIngredient) => ({ ...prevIngredient, [name]: value }));
  };

  // Fonction pour gérer le changement de fichier (image)
  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0]; // Récupère le premier fichier sélectionné
    if (selectedFile) {
      setFile(selectedFile); // Sauvegarde le fichier dans l'état
      const reader = new FileReader();

      reader.onloadend = () => {
        // Prévisualisation de l'image
        setImagePreview(reader.result);
      };

      reader.onerror = (error) => {
        console.error('Erreur lors de la lecture du fichier image:', error);
        alert("Une erreur est survenue lors de la lecture de l'image.");
      };

      reader.readAsDataURL(selectedFile); // Lire l'image en base64 pour la prévisualisation
    }
  };

  // Fonction pour télécharger l'image
  const uploadImage = async (file) => {
    const formData = new FormData();
    formData.append("image", file); // Ajout du fichier à FormData

    try {

      const uploadResponse = await fetch("http://localhost:3100/img/upload-image", {
        method: "POST",
        body: formData,
      });

      if (!uploadResponse.ok) {
        throw new Error("Erreur lors de l'upload de l'image");
      }


      const { imagePath } = await uploadResponse.json();

      return imagePath;
    } catch (error) {
      console.error("Erreur lors de l'upload de l'image:", error);
      alert("Erreur lors du téléchargement de l'image.");
      throw error;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Vérifier que tous les champs sont remplis
    if (!ingredient.name || !ingredient.description || !ingredient.prix || !file) {
      return alert("Tous les champs doivent être remplis, y compris l'image.");
    }

    try {
      // 1. Upload de l'image et récupération du chemin
      const imagePath = await uploadImage(file);
        console.log(imagePath);
      // 2. Ajouter l'image à l'objet de l'ingrédient
      const ingredientWithImage = { ...ingredient, photo: imagePath };

      // 3. Soumettre l'ingrédient avec l'image
      const response = await fetch(url + "/ingredients", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(ingredientWithImage),
      });

      if (response.ok) {
        alert("Ingrédient ajouté avec succès !");
        setIngredient({ name: "", description: "", photo: "", prix: "" });
        setImagePreview("");
        setFile(null);
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

        {/* Section pour la photo (image) */}
        <div className="form-section">
          <div className="form-group">
            <label htmlFor="photo">Photo de l'Ingrédient</label>
            <input
              type="file"
              id="photo"
              name="photo"
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
