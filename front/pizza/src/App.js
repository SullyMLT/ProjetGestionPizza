import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Link, Navigate } from 'react-router-dom';
import AddPizza from './components/AddPizza';
import PizzaList from './components/PizzaList';
import Login from './components/Login';
import Register from './components/Register';
import CommentList from './components/CommList';  // Import du composant CommentList
import AddIngredient from './components/AddIngredient'; // Import du composant AddIngredient
import IngredientList from './components/IngredientList'; // Import du composant IngredientList
import "./App.css";

function App() {

  const [user, setUser] = useState(() => JSON.parse(localStorage.getItem('user')));  // Récupérer l'utilisateur stocké
  const [error, setError] = useState(null); // État pour gérer les erreurs

  const login = (username, role) => {
    const newUser = { username, role };
    setUser(newUser);
    localStorage.setItem('user', JSON.stringify(newUser)); // Sauvegarder l'utilisateur dans le localStorage
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('user'); // Retirer l'utilisateur du localStorage
  };

  return (
    <div className="App">
      <h1>Gestion des Pizzas</h1>
      <nav>
        <Link to="/">Liste des pizzas</Link>
        {user && user.role === 'admin' && (
          <>
            | <Link to="/ajouter-pizza">Ajouter une pizza</Link> |
            <Link to="/ajouter-ingredient">Ajouter un ingrédient</Link> |
            <Link to="/ingredients">Liste des ingrédients</Link>
          </>
        )}
        |
        {/* Ajouter le lien pour les commentaires */}
        <Link to="/commentaires">Commentaires</Link>

        {user ? (
          <>
            | <button onClick={logout}>Se déconnecter</button>
          </>
        ) : (
          <>
            | <Link to="/login">Se connecter</Link> |
            <Link to="/register">S'inscrire</Link>
          </>
        )}
      </nav>

      {error && <p>{error}</p>} {/* Affichage des erreurs d'API */}

      <Routes>
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
        <Route path="/" element={<PizzaList />} />
        <Route
          path="/login"
          element={user ? <Navigate to="/" /> : <Login login={login} />}
        />
        <Route
          path="/register"
          element={user ? <Navigate to="/" /> : <Register />}
        />

        {/* Ajouter la route pour afficher les commentaires */}
        <Route path="/commentaires" element={<CommentList />} />
      </Routes>

    </div>
  );
}

export default App;
