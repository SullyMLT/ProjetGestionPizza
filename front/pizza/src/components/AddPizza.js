import React, { useState, useEffect } from "react";
import "./css/AddPizza.css";
import { url_host } from '../config/config.js';

const url = url_host;

function AddPizza() {
  const [ingredients, setIngredients] = useState([]);
  const [pizza, setPizza] = useState({
    nom: "",
    description: "",
    photo: "",
    prix: "0",
  });
  const [selectedIngredients, setSelectedIngredients] = useState([]);
  const [file, setFile] = useState(null);
  const [imagePreview, setImagePreview] = useState("");

  useEffect(() => {
    const fetchIngredients = async () => {
      try {
        const ingredientsResponse = await fetch(url + '/ingredients');
        if (!ingredientsResponse.ok) {
          throw new Error('Erreur lors de la récupération des ingrédients');
        }
        const allIngredientsData = await ingredientsResponse.json();
        setIngredients(allIngredientsData);
      } catch (error) {
        console.error('Erreur lors de la récupération des ingrédients:', error);
      }
    };

    fetchIngredients();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPizza((prevPizza) => ({ ...prevPizza, [name]: value }));
  };

  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile) {
      setFile(selectedFile);
      const reader = new FileReader();
      reader.onloadend = () => setImagePreview(reader.result);
      reader.readAsDataURL(selectedFile);
    }
  };

  const handleIngredientChange = (e) => {
    const ingredient = ingredients.find(ing => ing.id === parseInt(e.target.value));
    if (!ingredient) return;

    setSelectedIngredients((prevSelected) =>
      e.target.checked
        ? [...prevSelected, ingredient]
        : prevSelected.filter((ing) => ing.id !== ingredient.id)
    );
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!pizza.nom || !pizza.description || !file || selectedIngredients.length === 0) {
      return alert("Tous les champs doivent être remplis, y compris l'image et les ingrédients.");
    }

    try {
      const formData = new FormData();
      formData.append("image", file);

      console.log("Envoi de l'image...");
      const uploadResponse = await fetch('http://localhost:3100/img/upload-image', {
        method: 'POST',
        body: formData,
      });

      if (!uploadResponse.ok) {
        throw new Error("Erreur lors du téléchargement de l'image");
      }

      const { imagePath } = await uploadResponse.json();
      console.log("Image téléchargée avec succès :", imagePath);

      const updatedPizza = { ...pizza, photo: imagePath };

      console.log("Ajout de la pizza...");
      const pizzaResponse = await fetch(url+"/pizzas", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedPizza),
      });

      if (!pizzaResponse.ok) {
        throw new Error("Erreur lors de la création de la pizza");
      }

      const pizzaResponseData = await pizzaResponse.json();
      console.log("Pizza créée :", pizzaResponseData);

      const standardData = {
        pizza: pizzaResponseData,
        ingredients: selectedIngredients,
      };

      console.log("Création du standard...");
      const standardResponse = await fetch(url+"/standards", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(standardData),
      });

      if (!standardResponse.ok) {
        throw new Error("Erreur lors de la création du standard");
      }

      alert("Pizza ajoutée avec succès !");
      setPizza({ nom: "", description: "", photo: "", prix: "0" });
      setSelectedIngredients([]);
      setImagePreview("");
      setFile(null);

    } catch (error) {
      console.error("Erreur lors de l'ajout de la pizza :", error);
      alert(error.message || "Erreur lors de l'ajout de la pizza");
    }
  };

  return (
    <div className="add-pizza-form">
      <h2>Ajouter une Pizza</h2>
      <form onSubmit={handleSubmit} className="pizza-form">
        {["nom", "description"].map((field) => (
          <div className="form-group" key={field}>
            <label htmlFor={field}>
              {field.charAt(0).toUpperCase() + field.slice(1)}
            </label>
            <input
              type="text"
              id={field}
              name={field}
              value={pizza[field]}
              onChange={handleChange}
              placeholder={`Entrez ${field}`}
              required
            />
          </div>
        ))}

        <div className="form-group">
          <h2>Ingrédients</h2>
          <div className="ingredients-checkboxes">
            {ingredients.map((ingredient) => (
              <div key={ingredient.id} className="ingredient-item">
                <input
                  type="checkbox"
                  value={ingredient.id}
                  onChange={handleIngredientChange}
                />
                <label>{ingredient.name}</label>
              </div>
            ))}
          </div>
        </div>

        <div className="form-group">
          <label htmlFor="photo">Photo</label>
          <input
            type="file"
            id="photo"
            name="photo"
            onChange={handleFileChange}
            accept="image/*"
          />
        </div>

        {imagePreview && (
          <div className="image-preview">
            <img
              src={imagePreview}
              alt="Prévisualisation"
              style={{ maxWidth: '100%', maxHeight: '300px', objectFit: 'contain' }}
            />
          </div>
        )}

        <button type="submit" className="button">
          Ajouter une Pizza
        </button>
      </form>
    </div>
  );
}

export default AddPizza;
