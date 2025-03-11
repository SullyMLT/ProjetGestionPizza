import React, { useState } from "react";
import "../App.css";
function AddPizza({ addPizza }) {
  const [pizza, setPizza] = useState({
    nom: "",
    description: "",
    photo: "",
    prix: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPizza({ ...pizza, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (pizza.nom && pizza.description && pizza.prix && pizza.photo) {
      addPizza(pizza);
      setPizza({ nom: "", description: "", photo: "", prix: "" });
    } else {
      alert("Tous les champs doivent être remplis");
    }
  };

  return (
    <div className="add-pizza-form">
      <h2>Ajouter une Pizza</h2>
      <form onSubmit={handleSubmit} className="pizza-form">
        <div className="form-group">
          <label htmlFor="nom">Nom</label>
          <input
            type="text"
            id="nom"
            name="nom"
            value={pizza.nom}
            onChange={handleChange}
            placeholder="Nom de la pizza"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="description">Description</label>
          <textarea
            id="description"
            name="description"
            value={pizza.description}
            onChange={handleChange}
            placeholder="Description de la pizza"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="photo">URL de la photo</label>
          <input
            type="url"
            id="photo"
            name="photo"
            value={pizza.photo}
            onChange={handleChange}
            placeholder="Lien vers une image"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="prix">Prix</label>
          <input
            type="number"
            id="prix"
            name="prix"
            value={pizza.prix}
            onChange={handleChange}
            placeholder="Prix de la pizza"
            required
          />
        </div>

        <button type="submit" className="submit-btn">Ajouter la Pizza</button>
      </form>
    </div>
  );
}

export default AddPizza;
