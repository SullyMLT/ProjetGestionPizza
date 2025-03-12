import React, { useState } from "react";
import "../App.css";

function AddIngredient({ addIngredient }) {
  const [ingredient, setIngredient] = useState({
    name: "",
    description: "",
    pathPhoto: "",
    prix: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setIngredient((prevIngredient) => ({ ...prevIngredient, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Vérifier que tous les champs sont remplis
    if (Object.values(ingredient).includes("")) {
      return alert("Tous les champs doivent être remplis");
    }

    try {
      const response = await fetch("http://172.28.133.124:8080/ingredients", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(ingredient),
      });

      if (response.ok) {
        setIngredient({ name: "", description: "", pathPhoto: "", prix: "" });
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
            <label htmlFor="name">name</label>
            <input
              type="text"
              id="name"
              name="name"
              value={ingredient.name}
              onChange={handleChange}
              placeholder="Entrez le name de l'ingrédient"
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

        {/* Section pour la pathPhoto */}
        <div className="form-section">
         
          <div className="form-group">
            <label htmlFor="pathPhoto">URL de la pathPhoto</label>
            <input
              type="text"
              id="pathPhoto"
              name="pathPhoto"
              value={ingredient.pathPhoto}
              onChange={handleChange}
              placeholder="Entrez l'URL de la pathPhoto"
              required
            />
          </div>
        </div>

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
