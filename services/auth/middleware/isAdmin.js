const User = require('../models/User');

const isAdmin = async (req, res, next) => {
  const userId = req.userId; // Récupérer l'ID de l'utilisateur connecté (par le token)

  try {
    const user = await User.findById(userId);
    //if (!user || user.role !== 'admin') {
      //return res.status(403).json({ message: 'Accès interdit : vous devez être un admin' });
    //}
    next(); // Si l'utilisateur est un admin, on passe à la route suivante
  } catch (error) {
    res.status(500).json({ message: 'Erreur lors de la vérification de l\'admin', error });
  }
};

module.exports = isAdmin;