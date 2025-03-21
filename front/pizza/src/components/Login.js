import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

import "./css/Login.css";

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [token, setToken] = useState(localStorage.getItem('token') || ''); // récupère le token depuis le localStorage
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Envoyer le nom d'utilisateur et le mot de passe au backend pour la validation
      const response = await axios.post('http://localhost:3100/auth/login', { username, password });

      // Si la connexion réussit, stocker le token dans le localStorage
      localStorage.setItem('token', response.data.token);
      setToken(response.data.token);
      setErrorMessage('');

      navigate('/');
      window.location.reload();

    } catch (error) {
      setErrorMessage(error.response ? error.response.data.message : 'Erreur inconnue');
    }
  };

  return (
    <div className="login-container">
      <h2 className="login-title">Se connecter</h2>
      <form className="login-form" onSubmit={handleSubmit}>
        <input
          type="text"
          className="login-input"
          placeholder="Nom d'utilisateur"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          className="login-input"
          placeholder="Mot de passe"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit" className="login-button">Se connecter</button>
      </form>
      {errorMessage && <p className="login-error">{errorMessage}</p>}
    </div>
  );
}

export default Login;
