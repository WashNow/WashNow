import React from 'react';
import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
  RouterProvider,
  Navigate
} from 'react-router-dom';

import Perfil from './Pages/Perfil';
import Mapa from './Pages/Mapa';
import Reservar from './Pages/Reservar';
import LoginPage from './Pages/LoginPage';
import SignUpPage from './Pages/SignUpPage';
import OwnerHomePage from './Pages/OwnerHome';
import AddStation from './Pages/AddStation';
import ProtectedRoute from './Components/ProtectedRoute';

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/">
      <Route index element={<LoginPage />} />
      <Route path="signup" element={<SignUpPage />} />

      <Route element={<ProtectedRoute allowedRoles={['DRIVER']} />}>
        <Route path="Perfil" element={<Perfil />} />
        <Route path="Mapa" element={<Mapa />} />
        <Route path="Reservar" element={<Reservar />} />
      </Route>

      <Route element={<ProtectedRoute allowedRoles={['OWNER']} />}>
        <Route path="OwnerHome" element={<OwnerHomePage />} />
        <Route path="add-station" element={<AddStation />} />
      </Route>

      <Route path="*" element={<Navigate to="/" replace />} />
    </Route>
  )
);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
