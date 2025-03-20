import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes, Link, Navigate } from "react-router-dom";
import axios from "axios";
import { useNavigate } from 'react-router-dom';


import PizzaList from './components/PizzaList';
import PizzaDetails from './components/PizzaDetails';
import PanierCommande from './components/PanierCommande';
import Login from './components/Login';
import Register from './components/Register';
import AddPizza from './components/AddPizza';
import AddIngredient from './components/AddIngredient';
import IngredientList from './components/IngredientList';
import CommendeList from './components/CommendeList';
import CommentairesListeAdmin from "./components/CommentaireListeAdmin";
import Statistique from "./components/Statistique";
import CommandeListeCompte from "./components/CommandeListeCompte"

import './App.css';

function App() {
  const [user, setUser] = useState(null);  // Stocke les informations de l'utilisateur

  const [token, setToken] = useState(localStorage.getItem('token')); // Récupère le token du localStorage

  // Fonction pour récupérer les informations de l'utilisateur et ses commandes
  const refreshUser = () => {
    if (token) {
      axios.get('http://localhost:3100/users/protected', {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(response => {
        const userData = response.data.user;
        setUser(userData); // Mettre à jour l'utilisateur
      })
      .catch(() => {
        setUser(null); // Si l'utilisateur n'est pas trouvé ou le token est invalide
      });
    }
  };

  const navigate = useNavigate();

  useEffect(() => {
    refreshUser();  // Rafraîchit les informations de l'utilisateur à chaque changement de token
  }, [token]);

  // Fonction de déconnexion
  const logout = () => {
    localStorage.removeItem('token'); // Supprimer le token
    setToken(null); // Met à jour l'état du token
    setUser(null); // Réinitialiser l'utilisateur

    navigate('/');
    window.location.reload(); // Recharger la page
  };

  return (
    <div className="App">
      <header className="header">
        <Link to="/panier">🧺Panier</Link>
      </header>

      <nav>
        <Link to="/">Liste des pizzas</Link>
        {user && user.role === 'admin' && (
          <>
            <Link to="/ajouter-pizza">Ajouter une pizza</Link>
            <Link to="/ajouter-ingredient">Ajouter un ingrédient</Link>
            <Link to="/ingredients">Liste des ingrédients</Link>
            <Link to="/commendes">Liste des commandes</Link>
          </>
        )}


        {token ? (
          <>
            <Link to="/commande">Mes commandes</Link>
            <Link to="/statistique-pizza">Statistique</Link>
            <button onClick={logout}>Se déconnecter</button>
          </>
        ) : (
          <>
            <Link to="/login">Se connecter</Link>
            <Link to="/register">S'inscrire</Link>
          </>
        )}
      </nav>

      <Routes>
        <Route path="/" element={<PizzaList />} />
        <Route path="/commande" element={user ? (<CommandeListeCompte compteId={user ? user.id : null}/>) : (<Navigate to="/" />)} />
        <Route path="/panier" element={user ? (<PanierCommande userID={user ? user.id : null}/>) : (<Navigate to="/" />)} />
        <Route  path="/pizzas/:id" element={user ? (<PizzaDetails userID={user ? user.id : null}/>) : (<Navigate to="/" />)}/>
        <Route path="/statistique-pizza" element={<Statistique />} />
        <Route path="/login" element={<Login refreshUser={refreshUser} />} />
        <Route path="/register" element={<Register />} />

        {/* Admin routes */}
        <Route path="/ajouter-pizza" element={user?.role === 'admin' ? <AddPizza /> : <Navigate to="/" />} />
        <Route path="/ajouter-ingredient" element={user?.role === 'admin' ? <AddIngredient /> : <Navigate to="/" />} />
        <Route path="/ingredients" element={user?.role === 'admin' ? <IngredientList /> : <Navigate to="/" />} />
        <Route path="/commendes" element={user?.role === 'admin' ? <CommendeList /> : <Navigate to="/" />} />

        
      </Routes>
    </div>
  );
}

export default App;
