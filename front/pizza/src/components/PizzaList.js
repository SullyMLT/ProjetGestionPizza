import React from 'react';
import "../App.css";

const PizzaList = ({ pizzas }) => {
  return (
    <section>
      <h2>Liste des Pizzas</h2>
      <div className="pizza-container">
        {pizzas.map(pizza => (
          <div key={pizza.id} className="pizza-tile">
            <div className="left">
              <h3>{pizza.nom}</h3>
              <p>{pizza.description}</p>
              <img src={pizza.photo} alt={pizza.nom} />
            </div>
            <div className="right">
              <p><strong>Prix: {pizza.prix}€</strong></p>
              <button className="add-to-cart">Ajouter au panier</button>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}

export default PizzaList;
