// backend/server.js
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const authRoutes = require('./routes/authRoutes');

const app = express();
const port = process.env.PORT || 5000;

const { MongoClient } = require('mongodb');

// URL de connexion à MongoDB
const uri = 'mongodb://localhost:27017';

// Créer un client MongoDB
const client = new MongoClient(uri);

// Fonction pour se connecter à la base de données
async function connectToDatabase() {
    try {
        // Connexion au client MongoDB
        await client.connect();
        console.log('Connexion établie');

        // Sélectionner la base de données
        const database = client.db('e22100765');


        const collection = database.collection('Compte');
        const clients = await collection.find().toArray();
        console.log(clients);
    } catch (err) {
        console.error('Erreur de connexion à MongoDB:', err);
    } finally {
        // Fermer la connexion
        await client.close();
    }
}

// Appel de la fonction de connexion
connectToDatabase();

// Routes
app.use('/api', authRoutes);

// Démarrer le serveur
app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});
