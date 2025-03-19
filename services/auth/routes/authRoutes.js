const express = require('express');
const jwt = require('jsonwebtoken');
const axios = require('axios');
const { verifyToken } = require('../middleware/isAdmin');
const router = express.Router();
const privateKey = 'zjerYhe+7V';

// URL de l'API Spring Boot
const apiBaseUrl = 'http://localhost:8080/comptes';

// Route de connexion
router.post('/login', async (req, res) => {
  const { username, password } = req.body;

  try {
    // Appeler l'API Spring Boot pour authentifier l'utilisateur
    console.log('tentative');
    const response = await axios.post(`${apiBaseUrl}/connexion`, { username, password });
      const user = response.data;
      // Créer un token JWT pour l'utilisateur
      const token = jwt.sign(
        { data: { id: user.id, role: user.role } },
        privateKey,
        { expiresIn: '1h' }
      );
      if(user.id === -1){
        res.status(401).json({ message: 'Utilisateur/Mot de passe incorrect' });
      }else{
        res.json({ message: 'Connexion réussie', token });
      }

  } catch (error) {
    console.error('Erreur serveur:', error);
    res.status(500).json({ message: 'Erreur lors de la connexion', error });
  }
});

// Route d'inscription
router.post('/register', async (req, res) => {
  const { username, password, role } = req.body;

  try {
    // Appeler l'API Spring Boot pour créer l'utilisateur
    const response = await axios.post(apiBaseUrl, { username, password, role: role || 'client' });

    res.status(201).json({ message: 'Utilisateur créé avec succès' });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Erreur lors de l\'inscription', error });
  }
});

module.exports = router;
