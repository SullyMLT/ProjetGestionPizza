import React, { useState } from 'react';
import axios from 'axios';
import "../App.css";
const Register = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('client');  // Rôle par défaut

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:5000/api/register', {
        username,
        password,
        role,
      });
      alert('Inscription réussie');
    } catch (error) {
      console.error('Erreur lors de l\'inscription', error);
      alert('Erreur lors de l\'inscription');
    }
  };

  return (
    <div>
      <h2>S'inscrire</h2>
      <form onSubmit={handleRegister}>
        <input type="text" placeholder="Nom d'utilisateur" value={username} onChange={(e) => setUsername(e.target.value)} required />
        <input type="password" placeholder="Mot de passe" value={password} onChange={(e) => setPassword(e.target.value)} required />
        <select value={role} onChange={(e) => setRole(e.target.value)}>
          <option value="client">Client</option>
          <option value="admin">Admin</option>
        </select>
        <button type="submit">S'inscrire</button>
      </form>
    </div>
  );
};

export default Register;
