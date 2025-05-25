import React from 'react';
import CarWashMap from '../Components/CarWashMap';
import Header from '../Components/nav/Header';

const Mapa = () => {
  return (
    <div style={{ height: '100vh' }}>
    <Header/>     
     <CarWashMap />
    </div>
  );
};

export default Mapa;
