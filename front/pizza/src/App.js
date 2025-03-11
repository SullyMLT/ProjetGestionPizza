import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import AddPizza from './components/AddPizza';
import PizzaList from './components/PizzaList';
import Login from './components/Login';
import Register from './components/Register';
import "./App.css";

function App() {
  const [pizzas, setPizzas] = useState([
    {
      id: 1,
      nom: "Margherita",
      description: "Tomate, mozzarella, basilic",
      photo: process.env.PUBLIC_URL + "/pizza-1498148703.jpg",
      prix: 10.99,
    },
    {
      id: 2,
      nom: "Pepperoni",
      description: "Tomate, mozzarella, pepperoni",
      photo: process.env.PUBLIC_URL + "/Pepperoni_Pizza_Beauty_1200x1200.webp",
      prix: 12.99,
    },
  ]);

  const [user, setUser] = useState(null);  // Gestion de l'utilisateur

  const addPizza = (pizza) => {
    setPizzas([...pizzas, { ...pizza, id: pizzas.length + 1 }]);
  };

  const login = (username) => {
    setUser({ username });
  };

  const logout = () => {
    setUser(null);
  };

  return (
    <div className="App">
      <h1>Gestion des Pizzas</h1>
      <nav>
        <Link to="/">Liste des pizzas</Link> |
        <Link to="/ajouter-pizza"> Ajouter une pizza</Link> |
        {user ? (
          <>
            <span>Bienvenue, {user.username}</span> |
            <button onClick={logout}>Se déconnecter</button>
          </>
        ) : (
          <>
            <Link to="/login">Se connecter</Link> |
            <Link to="/register">S'inscrire</Link>
          </>
        )}
      </nav>

      <Routes>
        <Route path="/ajouter-pizza" element={<AddPizza addPizza={addPizza} />} />
        <Route path="/" element={<PizzaList pizzas={pizzas} />} />
        <Route path="/login" element={<Login login={login} />} />
        <Route path="/register" element={<Register />} />
      </Routes>
    </div>
  );
}

export default App;
