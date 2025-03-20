import React, { useState, useEffect } from "react";
import axios from "axios";
import "./css/CommentaireListeAdmin.css";

import { url_host } from '../config/config.js';
const url = url_host;

const CommentairesList = ({pizzaId}) => {
  const [commentaires, setCommentaires] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCommentaires = async () => {
      try {
        const response = await axios.get(`${url}/commentaires/pizza/${pizzaId}`);
        const commentairesData = response.data;

        const commentairesWithUsernames = await Promise.all(
          commentairesData.map(async (commentaire) => {
            const userResponse = await axios.get(`${url}/comptes/username/${commentaire.compteId}`);
            return { ...commentaire, username: userResponse.data };
          })
        );

        setCommentaires(commentairesWithUsernames);
      } catch (error) {
        setError(error.response ? error.response.data.message : error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCommentaires();
  }, [pizzaId]);

  if (loading) {
    return <p>Chargement des commentaires...</p>;
  }

  if (error) {
    return <p>Erreur: {error}</p>;
  }
  console.log("id pizza origine : "+pizzaId);

  return (
    <section>
      <h2>Commentaires :</h2>
      {commentaires.length === 0 ? (
        <p>Aucun commentaire pour cette pizza.</p>
      ) : (
        <div className="commentaires-container">
          {commentaires.map((commentaire) => (
            <div key={commentaire.id} className="commentaire-card">
              <p><strong>Nom d'utilisateur :</strong> {commentaire.username}</p>
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

export default CommentairesList;
