    import React, { useState, useEffect } from "react";
    import "../App.css";

    function AddPizza() {
      const [ingredients, setIngredients] = useState([]); // Liste d'ingrédients
      const [pizza, setPizza] = useState({
        nom: "",
        description: "",
        photo: "", // Contiendra le chemin relatif de l'image
        prix: "",
      });
      const [selectedIngredients, setSelectedIngredients] = useState([]); // Ingredients sélectionnés

      // UseEffect pour récupérer les ingrédients disponibles
      useEffect(() => {
        const fetchIngredients = async () => {
          try {
            const ingredientsResponse = await fetch('http://localhost:8080/ingredients');
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

      // State pour la prévisualisation de l'image
      const [imagePreview, setImagePreview] = useState("");

      const handleChange = (e) => {
        const { name, value } = e.target;
        setPizza((prevPizza) => ({ ...prevPizza, [name]: value }));
      };

      // Fonction pour gérer le changement de fichier (image)
      const handleFileChange = (e) => {
        const file = e.target.files[0]; // Récupère le premier fichier sélectionné
        if (file) {
          const reader = new FileReader();

          reader.onloadend = () => {
            // Prévisualisation de l'image
            setImagePreview(reader.result);

            // Génération du chemin relatif pour l'image (dans public)
            const imagePath = `/${pizza.nom}-${new Date().toISOString()}.jpg`; // Génère un nom unique basé sur le nom de la pizza et la date
            setPizza((prevPizza) => ({
              ...prevPizza,
              photo: imagePath, // Mettez à jour le chemin relatif de l'image
            }));
          };

          reader.onerror = (error) => {
            console.error('Erreur lors de la lecture du fichier image:', error);
            alert("Une erreur est survenue lors de la lecture de l'image.");
          };

          reader.readAsDataURL(file); // Lire l'image comme base64 pour la prévisualisation
        }
      };

      // Fonction pour gérer la sélection/désélection des ingrédients
      const handleIngredientChange = (e) => {
        const ingredientId = e.target.value;
        if (e.target.checked) {
          setSelectedIngredients((prevSelected) => [...prevSelected, ingredientId]);
        } else {
          setSelectedIngredients((prevSelected) =>
            prevSelected.filter((id) => id !== ingredientId)
          );
        }
         setSelectedIngredients((prevSelected) => [...prevSelected]);
      };

      const handleSubmit = async (e) => {
        e.preventDefault();
        // Vérifier que tous les champs sont remplis, y compris la photo
        if (Object.values(pizza).includes("") || !pizza.photo || selectedIngredients.length === 0) {
          return alert("Tous les champs doivent être remplis, y compris la photo et les ingrédients.");
        }
        try {
          const pizzaResponse = await fetch("http://localhost:8080/pizzas", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(pizza),
          });

          if (!pizzaResponse.ok) {
            throw new Error("Erreur lors de la création de la pizza");
          }

          // Récupérer l'ID de la pizza nouvellement créée
          const pizzaResponseData = await pizzaResponse.json();
          const pizzaId = pizzaResponseData.id;

          // Créer un standard avec l'ID de la pizza et les ingrédients
          const standardData = {
            pizza: { id: pizzaId }, // Ajouter l'ID de la pizza
            ingredients: selectedIngredients, // Ajouter les ingrédients sélectionnés
          };

          const standardResponse = await fetch("http://localhost:8080/standards", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(standardData),
          });

          if (standardResponse.ok) {
            alert("Pizza ajoutée avec succès !");
            setPizza({ nom: "", description: "", photo: "", prix: "" });
            setSelectedIngredients([]); // Réinitialiser les ingrédients sélectionnés
            setImagePreview(""); // Réinitialiser l'aperçu de l'image
          } else {
            throw new Error("Erreur lors de la création du standard");
          }
        } catch (error) {
          console.error("Erreur lors de l'envoi des données :", error);
          alert(error.message || "Erreur lors de l'ajout de la pizza et du standard");
        }
      };

      return (
        <div className="add-pizza-form">
          <h2>Ajouter une Pizza</h2>
          <form onSubmit={handleSubmit} className="pizza-form">
            {["nom", "description", "prix"].map((field) => (
              <div className="form-group" key={field}>
                <label htmlFor={field}>
                  {field.charAt(0).toUpperCase() + field.slice(1)}
                </label>
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

            {/* Afficher les checkboxes pour les ingrédients */}
            <div className="form-group">
              <label>Ingrédients</label>
              <div className="ingredients-checkboxes">
                {ingredients.map((ingredient) => (
                  <div key={ingredient.id}>
                    <label>
                      <input
                        type="checkbox"
                        value={ingredient.id}
                         onChange={handleIngredientChange}
                        checked={selectedIngredients.includes(ingredient.id)}

                      />
                      {ingredient.name}
                    </label>
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
                required
              />
            </div>

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

            <button type="submit" className="button">
              Ajouter une Pizza
            </button>
          </form>
        </div>
      );
    }

    export default AddPizza;
