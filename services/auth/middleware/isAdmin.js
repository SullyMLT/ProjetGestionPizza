const jwt = require('jsonwebtoken');
const privateKey = 'zjerYhe+7V';

const verifyToken = (req, res, next) => {
  const token = req.header('Authorization')?.split(' ')[1]; // Extraire le token depuis les headers

  if (!token) {
    return res.status(403).json({ message: 'Token manquant' });
  }

  try {
    const decoded = jwt.verify(token, privateKey); // Vérifier le token avec la clé privée
    req.user = decoded.data; // Ajouter les données de l'utilisateur à la requête
    next();
  } catch (error) {
    return res.status(400).json({ message: 'Token invalide' });
  }
};

// Middleware pour vérifier le rôle d'un utilisateur
const checkRole = (role) => {
  return (req, res, next) => {
    if (req.user.role !== role) {
      return res.status(403).json({ message: 'Accès interdit: rôle insuffisant' });
    }
    next();
  };
};

module.exports = { verifyToken, checkRole };
