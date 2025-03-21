import React, { useEffect, useState } from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import "./css/Statistique.css"

import { url_host } from '../config/config.js';

const url = url_host;

ChartJS.register(ArcElement, Tooltip, Legend);

const StatsPieChart = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const response = await fetch(url+'/statistiques');
        if (!response.ok) {
          throw new Error('Erreur lors de la récupération des données');
        }
        const data = await response.json();
        setData(data);
        setLoading(false);
      } catch (error) {
        setError(error.message);
        setLoading(false);
      }
    };

    fetchStats();
  }, []);

  if (loading) return <p>Chargement...</p>;
  if (error) return <p>Erreur: {error}</p>;

  const pizzaData = {
    labels: Object.keys(data.statPizza),
    datasets: [
      {
        label: 'Statistiques des Pizzas',
        data: Object.values(data.statPizza),
        backgroundColor: ['#FF5733', '#33FF57', '#3357FF', '#F1C40F'],
        borderColor: '#ffffff',
        borderWidth: 2,
      },
    ],
  };

  const ingredientData = {
    labels: Object.keys(data.statIngredient),
    datasets: [
      {
        label: 'Statistiques des Ingrédients',
        data: Object.values(data.statIngredient),
        backgroundColor: ['#FF8C00', '#8CFF00', '#00BFFF', '#FF1493'],
        borderColor: '#ffffff',
        borderWidth: 2,
      },
    ],
  };

  return (
    <div className="stats-container">
      <div className="chart">
        <h3>Statistiques des Pizzas</h3>
        <Pie data={pizzaData} />
      </div>
      <div className="chart">
        <h3>Statistiques des Ingrédients</h3>
        <Pie data={ingredientData} />
      </div>
    </div>
  );
};

export default StatsPieChart;