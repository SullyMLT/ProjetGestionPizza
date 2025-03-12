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
    setPizza((prevPizza) => ({ ...prevPizza, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Vérifier que tous les champs sont remplis
    if (Object.values(pizza).includes("")) {
      return alert("Tous les champs doivent être remplis");
    }

    try {
      const response = await fetch("http://172.28.133.124:8080/pizzas", {
        method: "POST",
        headers: {"Content-Type": "application/json",},
        body: JSON.stringify(pizza),
      });

      if (response.ok) {
        addPizza(pizza);
        setPizza({ nom: "", description: "", photo: "", prix: "" });
        alert("Pizza ajoutée avec succès !");
      } else {
        throw new Error("Erreur lors de l'ajout de la pizza");
      }
    } catch (error) {
      console.error(error);
      alert(error.message || "Erreur lors de l'ajout de la pizza");
    }
  };

  return (
    <div className="add-pizza-form">
      <h2>Ajouter une Pizza</h2>
      <form onSubmit={handleSubmit} className="pizza-form">
        {["nom", "description", "photo", "prix"].map((field) => (
          <div className="form-group" key={field}>
            <label htmlFor={field}>{field.charAt(0).toUpperCase() + field.slice(1)}</label>
            <input
              type={field === "prix" ? "number" : "text"}
              id={field}
              name={field}
              value={pizza[field]}
              onChange={handleChange}
              placeholder={`Entrez ${field === "prix" ? "le" : "la"} ${field}`}
              required
            />
          </div>
        ))}
        <button type="submit" className="button">Ajouter une Pizza</button>
      </form>
    </div>
  );
}

export default AddPizza;
