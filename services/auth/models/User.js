// backend/models/User.js
const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
  username: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  role: {
    type: String,
    required: true,
    enum: ['admin', 'client'],
    default: 'client',
  },
  activer: {
    type: Boolean,
    default: function () {
      return this.role === 'client'; // Les clients sont activés par défaut
    },
  },
});

module.exports = mongoose.model('User', userSchema);
