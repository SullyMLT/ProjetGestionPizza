const express = require('express');
const router = express.Router();
const commentaireService = require('../service/commentaireService');

// 🔹 Récupérer tous les commentaires
router.get('/', async (req, res) => {
  try {
    const commentaires = await commentaireService.getCommentaires();
    res.status(200).json(commentaires);
  } catch (error) {
    res.status(500).json({ message: 'Erreur serveur', error });
  }
});

// 🔹 Récupérer un commentaire par ID
router.get('/:id', async (req, res) => {
  try {
    const commentaire = await commentaireService.getCommentaireById(req.params.id);
    if (!commentaire) {
      return res.status(404).json({ message: 'Commentaire non trouvé' });
    }
    res.status(200).json(commentaire);
  } catch (error) {
    res.status(500).json({ message: 'Erreur serveur', error });
  }
});

// 🔹 Ajouter un commentaire
router.post('/', async (req, res) => {
  try {
    const commentaire = await commentaireService.addCommentaire(req.body);
    res.status(201).json(commentaire);
  } catch (error) {
    res.status(500).json({ message: 'Erreur lors de l\'ajout du commentaire', error });
  }
});

// 🔹 Supprimer un commentaire
router.delete('/:id', async (req, res) => {
  try {
    const commentaire = await commentaireService.deleteCommentaire(req.params.id);
    if (!commentaire) {
      return res.status(404).json({ message: 'Commentaire non trouvé' });
    }
    res.status(200).json({ message: 'Commentaire supprimé' });
  } catch (error) {
    res.status(500).json({ message: 'Erreur serveur', error });
  }
});

module.exports = router;
