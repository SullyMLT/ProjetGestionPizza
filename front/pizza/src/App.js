import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Link, Navigate } from 'react-router-dom';
import AddPizza from './components/AddPizza';
import PizzaList from './components/PizzaList';
import Login from './components/Login';
import Register from './components/Register';
import CommentList from './components/CommList';
import AddIngredient from './components/AddIngredient';
import IngredientList from './components/IngredientList';
import CommendeList from './components/CommendeList';
import PizzaDetails from './components/PizzaDetails';
import PanierCommande from './components/PanierCommande';

import "./App.css";

function App() {
  const [user, setUser] = useState(() => JSON.parse(localStorage.getItem('user')));
  const [panier, setPanier] = useState(() => JSON.parse(localStorage.getItem("panier")) || []); // Gérer le panier ici
  const [error, setError] = useState(null);

  // Fonction de connexion
  const login = (username, role) => {
    const newUser = { username, role };
    setUser(newUser);
    localStorage.setItem('user', JSON.stringify(newUser));
  };

  // Fonction de déconnexion
  const logout = () => {
    setUser(null);
    localStorage.removeItem('user');
    localStorage.removeItem('commandeId');  // Supprimer commandeId lors de la déconnexion
  };

  return (
    <div className="App">
      <header className="header">
        <Link to="/panier">🧺</Link>
      </header>
      <nav>
        <Link to="/">Liste des pizzas</Link>
        {user && user.role === 'admin' && (
          <>
            | <Link to="/ajouter-pizza">Ajouter une pizza</Link>
            | <Link to="/ajouter-ingredient">Ajouter un ingrédient</Link>
            | <Link to="/ingredients">Liste des ingrédients</Link>
            | <Link to="/commendes">Liste des commandes</Link>
          </>
        )}
        | <Link to="/commentaires">Liste des commentaires</Link>

        {user ? (
          <>
            | <button onClick={logout}>Se déconnecter</button>
          </>
        ) : (
          <>
            | <Link to="/login">Se connecter</Link>
            | <Link to="/register">S'inscrire</Link>
          </>
        )}
      </nav>

      {error && <p className="error">{error}</p>}

      <Routes>
        <Route path="/" element={<PizzaList />} />
        <Route path="/panier" element={<PanierCommande panier={panier} setPanier={setPanier} />} />

        {/* Routes sécurisées pour les admins */}
        <Route
          path="/ajouter-pizza"
          element={user && user.role === 'admin' ? <AddPizza /> : <Navigate to="/" />}
        />
        <Route
          path="/ajouter-ingredient"
          element={user && user.role === 'admin' ? <AddIngredient /> : <Navigate to="/" />}
        />
        <Route
          path="/ingredients"
          element={user && user.role === 'admin' ? <IngredientList /> : <Navigate to="/" />}
        />
        <Route
          path="/commendes"
          element={user && user.role === 'admin' ? <CommendeList /> : <Navigate to="/" />}
        />

        {/* Routes d'authentification */}
        <Route path="/login" element={user ? <Navigate to="/" /> : <Login login={login} />} />
        <Route path="/register" element={user ? <Navigate to="/" /> : <Register />} />

        {/* Route pour les commentaires */}
        <Route path="/commentaires" element={<CommentList />} />

        {/* Route pour afficher les détails d'une pizza */}
        <Route path="/pizzas/:id" element={<PizzaDetails />} />
      </Routes>
    </div>
  );
}

export default App;
