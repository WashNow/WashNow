import React from 'react';
import { Route, createBrowserRouter, createRoutesFromElements, RouterProvider } from 'react-router-dom';
import Home from './Pages/Home';
import Perfil from './Pages/Perfil';
import Mapa from './Pages/Mapa'
import Header from './Components/nav/Header';
import Reservar from './Pages/Reservar';
import LoginPage from './Pages/LoginPage';
import SignUpPage from './Pages/SignUpPage';
import OwnerHomePage from './Pages/OwnerHome';
import AddStation from './Pages/AddStation';



const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/">
      <Route index element={<LoginPage />} />
      <Route path="Perfil" element={<Perfil />} />
      <Route path="Mapa" element={<Mapa />} />
      <Route path="Reservar" element={<Reservar />} />
      <Route path="signup" element={<SignUpPage />} />
      <Route path="OwnerHome" element={<OwnerHomePage />} />
      <Route path="/add-station" element={<AddStation />} />


    </Route>
  )
)

function App({ routes }) {

  return (
    <>
      <RouterProvider router={router} />
    </>
  );
}

export default App;
