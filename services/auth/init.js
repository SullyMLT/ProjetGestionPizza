// Création de la base de données "e22102349" (si elle n'existe pas déjà)
db = db.getSiblingDB('e22100765');

// Création de la collection "Compte"
db.createCollection('Compte');

// Insertion des données pour la collection "Compte"
db.Compte.insertMany([
  {
    id: 1,
    pseudo: "alice.dupont",
    motdepasse: "alice123",  // Attention, c'est une valeur non sécurisée, à hacher dans un vrai projet
    role: "admin",
    active: true
  },
  {
    id: 2,
    pseudo: "bob.martin",
    motdepasse: "bob123",
    role: "client",  // Changement de "user" à "client"
    active: true
  },
  {
    id: 3,
    pseudo: "charlie.durand",
    motdepasse: "charlie123",
    role: "client",  // Changement de "user" à "client"
    active: false
  },
  {
    id: 4,
    pseudo: "diane.leroy",
    motdepasse: "diane123",
    role: "admin",
    active: true
  },
  {
    id: 5,
    pseudo: "edouard.perrin",
    motdepasse: "edouard123",
    role: "client",  // Changement de "user" à "client"
    active: true
  },
  {
    id: 6,
    pseudo: "francois.bernard",
    motdepasse: "francois123",
    role: "admin",
    active: false
  },
  {
    id: 7,
    pseudo: "gabrielle.moreau",
    motdepasse: "gabrielle123",
    role: "client",  // Changement de "user" à "client"
    active: true
  },
  {
    id: 8,
    pseudo: "hugo.renard",
    motdepasse: "hugo123",
    role: "client",  // Changement de "user" à "client"
    active: true
  },
  {
    id: 9,
    pseudo: "isabelle.roux",
    motdepasse: "isabelle123",
    role: "admin",
    active: true
  },
  {
    id: 10,
    pseudo: "julien.faure",
    motdepasse: "julien123",
    role: "client",  // Changement de "user" à "client"
    active: true
  }
]);
