import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Link, useLocation } from 'react-router-dom';
import AddPizza from './components/AddPizza';
import PizzaList from './components/PizzaList';
import Login from './components/Login';
import Register from './components/Register';
import "./App.css";

function App() {
  const [pizzas, setPizzas] = useState([]); // Liste des pizzas vide initialement
  const [user, setUser] = useState(() => JSON.parse(localStorage.getItem('user')));  // Récupérer l'utilisateur stocké
  const [error, setError] = useState(null); // État pour gérer les erreurs
  const location = useLocation(); // Utilisation de useLocation pour obtenir l'URL actuelle

  // Fonction pour récupérer les pizzas depuis l'API
  useEffect(() => {
    fetch('http://localhost:8080/pizzas')
      .then((response) => response.json())
      .then((data) => setPizzas(data)) // Mettre à jour l'état des pizzas avec les données récupérées
      .catch((error) => {
        setError("Erreur de récupération des pizzas");
        console.error("Erreur de récupération des pizzas :", error);
      });
  }, []); // L'appel API se fait une seule fois au montage du composant

  const addPizza = (pizza) => {
    setPizzas([...pizzas, { ...pizza, id: pizzas.length + 1 }]);
  };

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
        <Link to="/">Liste des pizzas</Link> |
        {user && user.role === 'admin' && (
          <>
            |
            <Link to="/ajouter-pizza"> Ajouter une pizza</Link>
          </>
        )}

        {user ? (
          <>
           |
            <button onClick={logout}>Se déconnecter</button>
          </>
        ) : (
          <>
            <Link to="/login">Se connecter</Link> |
            <Link to="/register">S'inscrire</Link>
          </>
        )}
      </nav>

      {error && <p>{error}</p>} {/* Affichage des erreurs d'API */}

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
