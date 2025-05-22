import React from 'react';
import { Route, createBrowserRouter, createRoutesFromElements, RouterProvider } from 'react-router-dom';
import Home from './Pages/Home';
import Perfil from './Pages/Perfil';
import Mapa from './Pages/Mapa'
import Header from './Components/nav/Header';
import Reservar from './Pages/Reservar';




const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<Header />}>
      <Route index element={<Home />} />
      <Route path="Perfil" element={<Perfil />} />
      <Route path="Mapa" element={<Mapa />} />
      <Route path="Reservar" element={<Reservar />} />
    </Route>
  )
)

function App({routes}) {

  return (
    <>
      <RouterProvider router={router}/>
    </>
  );
}

export default App;
