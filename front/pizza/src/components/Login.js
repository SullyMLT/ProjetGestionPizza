import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginUser } from './api';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = await loginUser(username, password);
      localStorage.setItem('token', token); // Enregistre le token dans le localStorage
      navigate('/'); // Redirige vers la page principale après la connexion
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
          <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} required />
        </div>
        <div>
          <label>Mot de passe</label>
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        </div>
        <button type="submit">Se connecter</button>
        {error && <p style={{ color: 'red' }}>{error}</p>}
      </form>
    </div>
  );
};

export default Login;
