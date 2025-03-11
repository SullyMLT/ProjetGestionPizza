const express = require("express");
const mongoose = require('mongoose');
const app = express();

const PORT = 3100;

app.get("/", (req, res) => {
    res.send("Hello, Express!");
});

const mongoURI = "mongodb://localhost:27017/e22100765";  // Remplace 'mydatabase' par le nom de ta base de données

mongoose.connect(mongoURI, { useNewUrlParser: true, useUnifiedTopology: true })
  .then(() => console.log('Connexion à MongoDB réussie'))
  .catch(err => console.error('Erreur de connexion MongoDB:', err));

app.listen(PORT, () => {
    console.log(`Serveur démarré sur http://localhost:${PORT}`);
});