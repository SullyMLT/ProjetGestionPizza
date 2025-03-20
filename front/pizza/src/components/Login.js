import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Import useNavigate from React Router

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [token, setToken] = useState(localStorage.getItem('token') || ''); // Récupérer le token depuis le localStorage
  const navigate = useNavigate(); // Initialize useNavigate

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Envoyer le nom d'utilisateur et le mot de passe au backend pour la validation
      const response = await axios.post('http://localhost:3100/auth/login', { username, password });

      // Si la connexion réussit, stocker le token dans le localStorage
      localStorage.setItem('token', response.data.token);
      setToken(response.data.token);
      setErrorMessage('');

      // Rediriger vers la racine après une connexion réussie
      navigate('/');  // This will take the user to the root path
      window.location.reload();

    } catch (error) {
      setErrorMessage(error.response ? error.response.data.message : 'Erreur inconnue');
    }
  };

  return (
    <div>
      <h2>Se connecter</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Nom d'utilisateur"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Mot de passe"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Se connecter</button>
      </form>
      {errorMessage && <p>{errorMessage}</p>}
    </div>
  );
}

export default Login;
