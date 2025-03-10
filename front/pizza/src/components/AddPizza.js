import React, { useState } from 'react';
import axios from 'axios';

const AddPizza = () => {
  const [nom, setNom] = useState('');
  const [description, setDescription] = useState('');
  const [photo, setPhoto] = useState('');
  const [prix, setPrix] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();

    const pizzaData = {
      nom,
      description,
      photo,
      prix: parseFloat(prix),
    };

    axios.post('http://localhost:5000/pizzas', pizzaData)
      .then(response => {
        alert('Pizza ajoutée avec succès !');
      })
      .catch(error => {
        console.error('Erreur:', error);
        alert('Erreur lors de l\'ajout de la pizza.');
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>Nom de la Pizza:</label>
      <input type="text" value={nom} onChange={e => setNom(e.target.value)} required />

      <label>Description:</label>
      <input type="text" value={description} onChange={e => setDescription(e.target.value)} required />

      <label>Photo URL:</label>
      <input type="text" value={photo} onChange={e => setPhoto(e.target.value)} />

      <label>Prix:</label>
      <input type="number" value={prix} onChange={e => setPrix(e.target.value)} required />

      <button type="submit">Ajouter Pizza</button>
    </form>
  );
};

export default AddPizza;
