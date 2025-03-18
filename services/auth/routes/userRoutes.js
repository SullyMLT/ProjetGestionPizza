const express = require('express');
const router = express.Router();
const { verifyToken, checkRole } = require('../middleware/isAdmin');


router.get('/protected', verifyToken, (req, res) => {
  res.json({ message: "Accès autorisé", user: req.user });
});


router.post('/admin-action', verifyToken, checkRole('admin'), (req, res) => {
  res.json({ message: "Action admin autorisée" });
});

module.exports = router;
