import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

import "./css/Login.css";

function Register() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post('http://localhost:3100/auth/register', { username, password });
      setErrorMessage('');
      alert('Inscription réussie ! Vous pouvez maintenant vous connecter.');

      navigate('/');
      window.location.reload();
    } catch (error) {
      setErrorMessage("Erreur lors de l'inscription");
    }
  };

  return (
    <div className="register-container">
      <h2 className="register-title">S'inscrire</h2>
      <form className="register-form" onSubmit={handleSubmit}>
        <input
          type="text"
          className="register-input"
          placeholder="Nom d'utilisateur"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          className="register-input"
          placeholder="Mot de passe"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit" className="register-button">S'inscrire</button>
      </form>
      {errorMessage && <p className="register-error">{errorMessage}</p>}
    </div>
  );
}

export default Register;
