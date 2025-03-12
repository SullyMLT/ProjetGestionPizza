import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode'; // Utilisation de l'export nommé

const Login = ({ login }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:3100/auth/login', {
        username,
        password,
      });

      const { token } = response.data;
      localStorage.setItem('token', token); // Stocke le token dans le localStorage

      // Décoder le token pour récupérer l'utilisateur et son rôle
      const decodedToken = jwtDecode(token); // Décoder le token
      login(decodedToken.data.ident, decodedToken.data.role); // Sauvegarder l'utilisateur avec son rôle

      navigate('/'); // Rediriger après la connexion
    } catch (error) {
      setError('Utilisateur ou mot de passe incorrect');
    }
  };

  return (
    <div>
      <h2>Se connecter</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Nom d'utilisateur</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Mot de passe</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Se connecter</button>
        {error && <p style={{ color: 'red' }}>{error}</p>}
      </form>
    </div>
  );
};

export default Login;
