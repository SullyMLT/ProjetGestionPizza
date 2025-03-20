const multer = require("multer");
const express = require('express');
const path = require("path");
const router = express.Router();
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, path.join(__dirname, "../../../front/pizza/public")); // Stocke directement dans `public`
  },
  filename: function (req, file, cb) {
    cb(null, file.originalname); // Garde le nom d'origine (ou utilise un nom unique)
  },
});

const upload = multer({ storage: storage });

router.post("/upload-image", upload.single("image"), (req, res) => {
  if (!req.file) {
    return res.status(400).json({ error: "Aucun fichier reçu" });
  }

  const imagePath = `/${req.file.filename}`; // L'image sera accessible à la racine de `public`
  res.json({ imagePath });
});

module.exports = router;