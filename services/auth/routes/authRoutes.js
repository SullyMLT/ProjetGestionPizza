// backend/routes/authRoutes.js
const express = require('express');
const jwt = require('jsonwebtoken');
const User = require('../models/User');
const isAdmin = require('../middleware/isAdmin');  // Import du middleware
const router = express.Router();
const privateKey = "zjerYhe+7V";

// Route d'inscription
router.post('/register', async (req, res) => {
  const { username, password, role } = req.body;

  try {
    const userExists = await User.findOne({ username });
    if (userExists) {
      return res.status(400).json({ message: 'Nom d\'utilisateur déjà utilisé' });
    }

    const newUser = new User({ username, password, role: role || 'client' });
    await newUser.save();

    res.status(201).json({ message: 'Utilisateur créé avec succès' });
  } catch (error) {
    res.status(500).json({ message: 'Erreur lors de l\'inscription', error });
  }
});

const verifyToken = (req, res, next) => {
  const token = req.header('Authorization')?.split(' ')[1]; // Extraire le token depuis les headers

  if (!token) {
    return res.status(403).json({ message: 'Token manquant' });
  }

  try {
    const decoded = jwt.verify(token, privateKey); // Vérifier le token avec la clé privée
    req.user = decoded.data; // Mettre les données du user dans la requête
    next();
  } catch (error) {
    return res.status(400).json({ message: 'Token invalide' });
  }
};

// Route pour obtenir les informations de l'utilisateur connecté
router.get('/profile', verifyToken, async (req, res) => {
  try {
    const user = await User.findOne({ username: req.user.ident }); // Utiliser l'identifiant extrait du token
    if (!user) {
      return res.status(404).json({ message: 'Utilisateur non trouvé' });
    }
    res.json({ username: user.username, role: user.role }); // Renvoie le nom d'utilisateur et le rôle
  } catch (error) {
    res.status(500).json({ message: 'Erreur lors de la récupération des informations', error });
  }
});
// Route de connexion
router.post('/login', async (req, res) => {
  const { username, password } = req.body;
  console.log(`Tentative de connexion: ${username}`); // Ajout d'un log

  try {
    const user = await User.findOne({ username });
    if (!user) {
      console.log('Utilisateur non trouvé');
      return res.status(404).json({ message: 'Utilisateur/Mot de passe incorrect' });
    }

    console.log(`Mot de passe envoyé: ${password}`);
    console.log(`Mot de passe en base: ${user.password}`);

    // Comparaison des mots de passe
    if (user.password !== password) {
      console.log('Mot de passe incorrect');
      return res.status(400).json({ message: 'Utilisateur/Mot de passe incorrect' });
    }

    if (!user.activer) {
      console.log('Utilisateur désactivé');
      return res.status(403).json({ message: 'Votre compte n\'est pas activé.' });
    }

    // Création du token avec le rôle inclus
    const token = jwt.sign(
      { data: { ident: user.username, role: user.role } }, // Inclure le rôle dans le token
      privateKey,
      { expiresIn: '1h' }
    );

    console.log('Connexion réussie');
    res.json({ res: 'Connexion réussie', token }); // Renvoyer le token avec le rôle
  } catch (error) {
    console.error('Erreur serveur:', error);
    res.status(500).json({ message: 'Erreur lors de la connexion', error });
  }
});


// Route pour récupérer tous les utilisateurs
router.get('/users', async (req, res) => {
  try {
    const users = await User.find(); // Récupère tous les utilisateurs
    res.json(users); // Renvoie les utilisateurs en réponse
  } catch (error) {
    res.status(500).json({ message: 'Erreur lors de la récupération des utilisateurs', error });
  }
});

// Route pour modifier l'état 'activer' d'un utilisateur
router.put('/user/:id', isAdmin, async (req, res) => {
  const { id } = req.params; // Récupère l'_id passé dans l'URL

  try {
    // Recherche l'utilisateur dans la base de données par son _id
    const user = await User.findById(id);

    if (!user) {
      return res.status(404).json({ message: 'Utilisateur non trouvé' });
    }

    // Inverse la valeur de 'activer'
    user.activer = !user.activer;

    // Sauvegarde les modifications dans la base de données
    await user.save();

    res.json({ message: 'Utilisateur mis à jour avec succès', user });
  } catch (error) {
    res.status(500).json({ message: 'Erreur lors de la mise à jour de l\'utilisateur', error });
  }
});

// Route pour supprimer un utilisateur
router.delete('/user/:id', isAdmin, async (req, res) => {
  const { id } = req.params; // Récupère l'_id passé dans l'URL
  const userId = req.userId; // Id de l'utilisateur connecté, supposé être extrait du token JWT

  try {
    // Recherche l'utilisateur à supprimer dans la base de données par son _id
    const userToDelete = await User.findById(id);

    if (!userToDelete) {
      return res.status(404).json({ message: 'Utilisateur non trouvé' });
    }

    // Supprime l'utilisateur de la base de données
    await userToDelete.delete();

    res.json({ message: 'Utilisateur supprimé avec succès' });
  } catch (error) {
    res.status(500).json({ message: 'Erreur lors de la suppression de l\'utilisateur', error });
  }
});

module.exports = router;
