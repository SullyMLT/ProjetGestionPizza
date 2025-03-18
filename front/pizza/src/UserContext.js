import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';

const UserContext = createContext();

const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const token = localStorage.getItem('token');

  const refreshUser = () => {
    if (token) {
      axios.get('http://localhost:5000/verify-token', {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(response => {
        setUser(response.data.user);  // Mettre à jour l'utilisateur
      })
      .catch(() => {
        setUser(null);  // Si le token est invalide, réinitialiser l'utilisateur
      });
    }
  };

  useEffect(() => {
    refreshUser();  // Rafraîchit les informations de l'utilisateur si un token est présent
  }, [token]);

  const logout = () => {
    localStorage.removeItem('token');
    setUser(null);
  };

  return (
    <UserContext.Provider value={{ user, refreshUser, logout }}>
      {children}
    </UserContext.Provider>
  );
};

export { UserProvider, UserContext };
