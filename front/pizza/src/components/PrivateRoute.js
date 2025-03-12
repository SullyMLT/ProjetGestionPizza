import React from 'react';
import { Route, Navigate } from 'react-router-dom';

// PrivateRoute : protège les routes réservées aux administrateurs
const PrivateRoute = ({ element, user, ...rest }) => {
  return (
    <Route
      {...rest}
      element={user && user.role === 'admin' ? element : <Navigate to="/" />}
    />
  );
};

export default PrivateRoute;
