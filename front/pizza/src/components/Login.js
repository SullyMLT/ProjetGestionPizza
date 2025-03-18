import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Login = ({ refreshUser }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Envoyer les informations de connexion au backend pour obtenir un token
      const response = await axios.post('http://localhost:3100/auth/login', { username, password });
      const token = response.data.token;

      // Stocker le token dans le localStorage
      localStorage.setItem('token', token);

      // Vérifier et obtenir les informations de l'utilisateur
      axios.get('http://localhost:3100/users/protected', {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(response => {
        const user = response.data.user;

        navigate('/'); // Rediriger vers la page d'accueil après la connexion
        window.location.reload();
      });

    } catch (err) {
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
