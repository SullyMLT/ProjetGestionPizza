// Connexion à la base de données
db = db.getSiblingDB('e22102349');

db.createCollection("Statistique");

var statistique = {
    _id: "1", // Spécifie l'id du document
    statPizza: {
        "Pizza Pepperoni": 1
    },
    statIngredient: {
        "Sauce Tomato": 1,
        "Crême fraiche": 1
    }
};

db.Statistique.insertOne(statistique);