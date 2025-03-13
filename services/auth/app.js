const express = require("express");
const mongoose = require('mongoose');
const authRoutes = require('./routes/authRoutes');  // Routes d'authentification
const commentaireController = require('./routes/commentaireController');
const app = express();
const cors = require('cors');

const PORT = 3100;

// Middleware pour parser les données JSON
app.use(express.json());  // Permet de parser les requêtes JSON
app.use(cors());
app.use('/auth', authRoutes);
app.use('/comm', commentaireController);

app.get("/", (req, res) => {
    res.send("Hello, Express!");
});

const mongoURI = "mongodb://localhost:27017/e22100765";  // Remplace par le nom de ta base de données

// Connexion à MongoDB
mongoose.connect(mongoURI, { useNewUrlParser: true, useUnifiedTopology: true })
  .then(() => console.log('Connexion à MongoDB réussie'))
  .catch(err => console.error('Erreur de connexion MongoDB:', err));

// Démarrage du serveur
app.listen(PORT, () => {
    console.log(`SZrveur démarré sur http://localhost:${PORT}`);
});
