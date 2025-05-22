import React, { useEffect, useRef, useState } from 'react';
import maplibregl from 'maplibre-gl';
import { useNavigate } from 'react-router-dom';
import 'maplibre-gl/dist/maplibre-gl.css';

import styles from './CarWashMap.module.css';

const carWashLocations = [
  {
    id: 1,
    name: "Lava Rápido Aveiro",
    address: "Rua do Brasil, 3810-123 Aveiro",
    coords: [-8.64554, 40.6405],
  },
  {
    id: 2,
    name: "Auto Lavagem Central",
    address: "Av. Dr. Lourenço Peixinho, 3810-105 Aveiro",
    coords: [-8.653, 40.644],
  },
  {
    id: 3,
    name: "Lavagem Express Glória",
    address: "Rua Eng. Von Haff, 3800-167 Aveiro",
    coords: [-8.6582, 40.6378],
    horario: "08:00-19:00"
  },
  {
    id: 4,
    name: "Eco Wash Vera Cruz",
    address: "Rua dos Combatentes da Grande Guerra, 3810-089 Aveiro",
    coords: [-8.6473, 40.6335],
    horario: "07:30-20:00"
  },
  {
    id: 5,
    name: "AquaClean São Bernardo",
    address: "Rua do Norte, 3800-551 Aveiro",
    coords: [-8.6421, 40.6489],
    servicos: ["Lavagem interior", "Aspiração", "Lavagem exterior"]
  },
  {
    id: 6,
    name: "LavAuto Esgueira",
    address: "Rua dos Bombeiros Voluntários, 3800-527 Aveiro",
    coords: [-8.6357, 40.6422],
    preco: "€8-15"
  },
  {
    id: 7,
    name: "Brilliant Wash Cacia",
    address: "EN235, 3800-614 Cacia",
    coords: [-8.6243, 40.6607],
    horario: "24 horas"
  }
];
const CarWashMap = () => {
  const navigate = useNavigate();

  const mapRef = useRef(null);
  const markersRef = useRef({});
  const mapInstance = useRef(null);
  const [selectedId, setSelectedId] = useState(null);

  useEffect(() => {
    const map = new maplibregl.Map({
      container: mapRef.current,
      style: 'https://tiles.stadiamaps.com/styles/alidade_smooth.json',
      center: [-8.64554, 40.6405],
      zoom: 13,
    });

    mapInstance.current = map;

    carWashLocations.forEach(loc => {
      const popup = new maplibregl.Popup().setHTML(
        `<strong>${loc.name}</strong><br/>${loc.address}<br/><em>Lugares disponíveis: ${loc.availableSlots}</em>`
      );

      const marker = new maplibregl.Marker({ color: '#1677ff' })
        .setLngLat(loc.coords)
        .setPopup(popup)
        .addTo(map);

      markersRef.current[loc.id] = { marker, popup };
    });

    return () => map.remove();
  }, []);

  const handleSelect = (location) => {
    setSelectedId(location.id);
    const map = mapInstance.current;
    const { marker, popup } = markersRef.current[location.id];

    map.flyTo({
      center: location.coords,
      zoom: 15,
      speed: 1.2,
    });

    popup.addTo(map);
  };

  const handleReserve = (location) => {
    if (location.availableSlots === 0) {
      alert('Este local está sem vagas disponíveis.');
      return;
    }

    // Aqui pode ir a lógica para reserva (API, modal, etc.)
    console.log(`Reservar: ${location.name}`);
    alert(`Reserva iniciada para ${location.name}`);
  };

  return (
    <div className={styles.container}>
      <div className={styles.list}>
        <h2>Locais de Lavagem em Aveiro</h2>
        <div>
          {carWashLocations.map(loc => (
            <div
              key={loc.id}
              className={`${styles.card} ${selectedId === loc.id ? styles.selected : ''}`}
              onClick={() => handleSelect(loc)}
            >
              <h3>{loc.name}</h3>
              <p>{loc.address}</p>
              <div className={styles.buttonGroup}>

                <button
                  className={styles.reserveButton}
                  disabled={loc.availableSlots === 0}
                  onClick={(e) => {
                    e.stopPropagation();
                    navigate('/reservar', { state: { locationData: loc } });
                  }}

                >
                  Reservar
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
      <div className={styles.map} ref={mapRef}></div>
    </div>
  );
};

export default CarWashMap;
