const axios = require('axios');

async function testLogin() {
  try {
    const response = await axios.post('http://localhost:3100/auth/login', {
      username: 'sallsl',  // Remplace par un utilisateur valide
      password: '123456789'   // Remplace par le bon mot de passe
    });

    console.log('✅ Réponse reçue:', response.data);
  } catch (error) {
    console.error('❌ Erreur:', error.response?.data || error.message);
  }
}

testLogin();
