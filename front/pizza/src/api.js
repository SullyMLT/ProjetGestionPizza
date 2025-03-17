import axios from 'axios';

const API_URL = 'http://localhost:3100/auth'; // Change this to match your backend URL

export const loginUser = async (username, password) => {
  try {
    const response = await axios.post(`${API_URL}/login`, { username, password });
    return response.data.token;
  } catch (error) {
    throw new Error('Erreur lors de la connexion');
  }
};

export const registerUser = async (username, password, role) => {
  try {
    const response = await axios.post(`${API_URL}/register`, { username, password, role });
    return response.data;
  } catch (error) {
    throw new Error('Erreur lors de l\'inscription');
  }
};
