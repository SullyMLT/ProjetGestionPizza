import React, { useState } from "react";
import "../App.css";
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

  // Gérer les changements dans les champs de texte
  const handleChange = (e) => {
    const { name, value } = e.target;
    setIngredient((prevIngredient) => ({ ...prevIngredient, [name]: value }));
  };

  // Gérer le changement de fichier (image)
 const handleFileChange = (e) => {
   const file = e.target.files[0]; // Récupère le premier fichier sélectionné
   if (file) {
     setImagePreview(URL.createObjectURL(file)); // Prévisualisation de l'image
     setIngredient((prevIngredient) => ({
       ...prevIngredient,
       pathPhoto: file, // Met à jour l'image
     }));
   }
 };

 const handleSubmit = async (e) => {
   e.preventDefault();

   // Vérifier que tous les champs sont remplis
   if (Object.values(ingredient).includes("") || !ingredient.pathPhoto) {
     return alert("Tous les champs doivent être remplis, y compris l'image.");
   }

   try {
     // 1️⃣ Étape 1 : Envoyer l'image
     const formData = new FormData();
     formData.append("image", ingredient.pathPhoto); // Envoi de l'image

     const uploadResponse = await fetch('http://localhost:3100/img/upload-image', {
       method: 'POST',
       body: formData,
     });

     if (!uploadResponse.ok) {
       throw new Error("Erreur lors du téléchargement de l'image");
     }

     // Récupérer l'URL de l'image depuis la réponse
     const { imagePath } = await uploadResponse.json();

     // 2️⃣ Étape 2 : Mettre à jour l'ingrédient avec le chemin de l'image
     const updatedIngredient = { ...ingredient, photo: imagePath };

     // 3️⃣ Étape 3 : Envoyer les données de l'ingrédient à l'API pour l'ajouter
     const response = await fetch(url + "/ingredients", {
       method: "POST",
       headers: {
         "Content-Type": "application/json",
       },
       body: JSON.stringify(updatedIngredient),
     });

     if (response.ok) {
       setIngredient({ name: "", description: "", pathPhoto: "", prix: "" });
       setImagePreview(""); // Réinitialiser la prévisualisation
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

        {/* Section pour le nom de l'ingrédient */}
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

        {/* Section pour la photo de l'ingrédient */}
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

        {/* Affichage de la prévisualisation de l'image */}
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
