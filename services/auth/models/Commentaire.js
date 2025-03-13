const mongoose = require('mongoose');

const commentaireSchema = new mongoose.Schema({
  description: { type: String, required: true },
  date: { type: Date, default: Date.now },
  pizzaOrigine: { type: Number, required: true },
  note: { type: Number, min: 0, max: 5, required: true }
});

module.exports = mongoose.model('Commentaire', commentaireSchema);
