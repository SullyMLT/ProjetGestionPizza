import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "./css/CommentaireListeAdmin.css";


import { url_host } from '../config/config.js';
const url = url_host;

const CommentairesListeAdmin = () => {
  const [commentaires, setCommentaires] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCommentaires = async () => {
      try {
        const response = await axios.get(`${url}/commentaires`);
        setCommentaires(response.data);
      } catch (error) {
        setError(error.response ? error.response.data.message : error.message);
      } finally {
        setLoading(false);
      }
    };
  
    fetchCommentaires();
  }, []);

  if (loading) {
    return <p>Chargement des commentaires...</p>;
  }

  if (error) {
    return <p>Erreur: {error}</p>;
  }

  return (
    <section>
      <h2>Commentaires :</h2>
      {commentaires.length === 0 ? (
        <p>Aucun commentaire pour cette pizza.</p>
      ) : (
        <div className="commentaires-container">
          {commentaires.map((commentaire) => (
            <div key={commentaire.id} className="commentaire-card">
              <p>Commentaire ID : {commentaire.id}</p>
              <p>Pizza ID : {commentaire.pizzaOrigine}</p>
              <p><strong>Note:</strong> {commentaire.note/2.0}/5</p>
              <p><strong>Description:</strong> {commentaire.description}</p>
              <p><small><strong>Date:</strong> {commentaire.date}</small></p>
            </div>
          ))}
        </div>
      )}
    </section>
  );
};

export default CommentairesListeAdmin;
