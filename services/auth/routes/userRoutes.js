const express = require('express');
const axios = require('axios');
const { verifyToken, checkRole } = require('../middleware/authMiddleware');
const router = express.Router();
const apiBaseUrl = 'http://172.28.133.124:8080/comptes';

// Route protégée pour récupérer tous les utilisateurs
router.get('/', verifyToken, checkRole('admin'), async (req, res) => {
  try {
    const response = await axios.get(apiBaseUrl);
    res.json(response.data);
  } catch (error) {
    res.status(500).json({ message: 'Erreur lors de la récupération des utilisateurs', error });
  }
});

// Route pour récupérer un utilisateur spécifique par ID
router.get('/:id', verifyToken, checkRole('admin'), async (req, res) => {
  const { id } = req.params;
  try {
    const response = await axios.get(`${apiBaseUrl}/${id}`);
    res.json(response.data);
  } catch (error) {
    res.status(500).json({ message: 'Erreur lors de la récupération de l\'utilisateur', error });
  }
});

module.exports = router;
