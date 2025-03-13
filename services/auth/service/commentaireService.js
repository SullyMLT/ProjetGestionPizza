const Commentaire = require('../models/Commentaire');

class CommentaireService {
  async getCommentaires() {
    return await Commentaire.find();
  }

  async getCommentaireById(id) {
    return await Commentaire.findById(id);
  }

  async addCommentaire(commentaireData) {
    const commentaire = new Commentaire(commentaireData);
    return await commentaire.save();
  }

  async deleteCommentaire(id) {
    return await Commentaire.findByIdAndDelete(id);
  }
}

module.exports = new CommentaireService();
