const express = require('express');
const jwt = require('jsonwebtoken');
const axios = require('axios');
const bcrypt = require('bcrypt');
const { verifyToken } = require('../middleware/isAdmin');
const router = express.Router();
const privateKey = 'zjerYhe+7V';

// URL de l'API Spring Boot
const apiBaseUrl = 'http://localhost:8080/comptes';

// Route de connexion
router.post('/login', async (req, res) => {
  const { username, password } = req.body;

  try {
   console.log('Mot de passe fourni:', password);
    // Appeler l'API Spring Boot pour authentifier l'utilisateur
    const response = await axios.post(`${apiBaseUrl}/connexion`, { username, password });
    const user = response.data;

    // Vérifier si l'utilisateur existe
    if (user.id === -1) {
      return res.status(401).json({ message: 'Utilisateur/Mot de passe incorrect' });
    }

    console.log('Mot de passe haché:', user.password);

    // Comparer le mot de passe haché avec celui fourni
    const passwordMatch = await bcrypt.compare(password, user.password);

    if (!passwordMatch) {
      return res.status(401).json({ message: 'Utilisateur/Mot de passe incorrect' });
    }

    // Créer un token JWT pour l'utilisateur
    const token = jwt.sign(
      { data: { id: user.id, role: user.role } },
      privateKey,
      { expiresIn: '1h' }
    );

    res.json({ message: 'Connexion réussie', token });

  } catch (error) {
    console.error('Erreur serveur:', error);
    res.status(500).json({ message: 'Erreur lors de la connexion', error });
  }
});

// Route d'inscription
router.post('/register', async (req, res) => {
  const { username, password, role } = req.body;

  try {
    // Hacher le mot de passe avant de l'enregistrer
    const hashedPassword = await bcrypt.hash(password, 10);

    // Appeler l'API Spring Boot pour créer l'utilisateur avec le mot de passe haché
    const response = await axios.post(apiBaseUrl, { username, password: hashedPassword, role: role || 'client' });

    res.status(201).json({ message: 'Utilisateur créé avec succès' });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Erreur lors de l\'inscription', error });
  }
});

module.exports = router;
