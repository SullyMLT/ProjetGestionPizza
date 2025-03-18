const jwt = require('jsonwebtoken');
const privateKey = 'zjerYhe+7V';

const verifyToken = (req, res, next) => {
  const token = req.header('Authorization')?.split(' ')[1];

  if (!token) {
    return res.status(403).json({ message: 'Token manquant' });
  }

  try {
    const decoded = jwt.verify(token, privateKey);
    req.user = decoded.data;
    next();
  } catch (error) {
    return res.status(400).json({ message: 'Token invalide' });
  }
};


const checkRole = (role) => {
  return (req, res, next) => {
    if (req.user.role !== role) {
      return res.status(403).json({ message: 'Accès interdit: rôle insuffisant' });
    }
    next();
  };
};

module.exports = { verifyToken, checkRole };
