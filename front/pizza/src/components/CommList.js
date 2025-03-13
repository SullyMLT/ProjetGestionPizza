import React, { useState, useEffect } from 'react';
import "../App.css";

// URL de l'API
const url = 'http://localhost:3100/comm';

const CommentList = () => {
  const [comments, setComments] = useState([]);  // État pour les commentaires
  const [loading, setLoading] = useState(true);  // État pour savoir si les données sont en cours de chargement
  const [error, setError] = useState(null);  // État pour gérer les erreurs

  useEffect(() => {
    // Fonction pour récupérer les commentaires depuis l'API
    const fetchComments = async () => {
      try {
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error('Erreur lors de la récupération des commentaires');
        }
        const data = await response.json();
        setComments(data);  // Mettre à jour l'état des commentaires avec les données récupérées
      } catch (error) {
        setError(error.message);  // Si une erreur survient, mettre à jour l'état d'erreur
      } finally {
        setLoading(false);  // Fin du chargement, mettre à jour l'état de chargement
      }
    };

    fetchComments();  // Appeler la fonction pour récupérer les commentaires
  }, []);  // Le tableau vide [] signifie que l'effet se déclenche une seule fois, lors du montage du composant

  if (loading) {
    return <p>Chargement des commentaires...</p>;  // Afficher un message de chargement tant que les données sont en cours de récupération
  }

  if (error) {
    return <p>Erreur: {error}</p>;  // Si une erreur survient, afficher un message d'erreur
  }

  return (
    <section>
      <h2>Liste des Commentaires</h2>
      <div className="comment-container">
        {comments.map(comment => (
          <div key={comment._id} className="comment-tile">
            <div className="left">
              <p>{comment.description}</p>
            </div>
            <div className="right">
              <p><strong>Note: {comment.note}/5</strong></p>
              <p>{new Date(comment.date).toLocaleString()}</p>  {/* Date au format lisible */}
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default CommentList;
