const express = require("express");
const mongoose = require('mongoose');
const authRoutes = require('./routes/authRoutes');  // Routes d'authentification

const userRoutes = require('./routes/userRoutes');
const app = express();
const cors = require('cors');


const PORT = 3100;

// Middleware pour parser les données JSON
app.use(express.json());  // Permet de parser les requêtes JSON
app.use(cors());
app.use('/auth', authRoutes);

app.use('/users', userRoutes);

app.get("/", (req, res) => {
    res.send("Hello, Express!");
});


// Démarrage du serveur
app.listen(PORT, () => {
    console.log(`SZrveur démarré sur http://localhost:${PORT}`);
});
