import React, { useEffect, useRef, useState } from 'react';
import maplibregl from 'maplibre-gl';
import { useNavigate } from 'react-router-dom';
import 'maplibre-gl/dist/maplibre-gl.css';
import styles from './CarWashMap.module.css';

const CarWashMap = () => {
  const navigate = useNavigate();
  const [carWashStations, setCarWashStations] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  const mapRef = useRef(null);
  const popupsRef = useRef({});
  const mapInstance = useRef(null);
  const [selectedId, setSelectedId] = useState(null);
  const user = JSON.parse(localStorage.getItem('user'));

  useEffect(() => {
    const fetchCarWashStations = async () => {
      try {
        const response = await fetch('/api/CarwashStations', {
          headers: {
            'Accept': 'application/json'
          }
        });

        if (!response.ok) {
          const errorText = await response.text();
          throw new Error(`Erro ${response.status}: ${errorText}`);
        }

        const contentType = response.headers.get('content-type');
        if (!contentType || !contentType.includes('application/json')) {
          const text = await response.text();
          throw new Error(`Resposta não é JSON: ${text.substring(0, 100)}...`);
        }

        const data = await response.json();
        setCarWashStations(data);
      } catch (err) {
        console.error('Erro ao buscar estações:', err);
        setError(`Falha ao carregar estações: ${err.message}`);
      } finally {
        setIsLoading(false);
      }
    };

    fetchCarWashStations();
  }, []);

  useEffect(() => {
    if (carWashStations.length === 0) return;

    const map = new maplibregl.Map({
      container: mapRef.current,
      style: 'https://tiles.stadiamaps.com/styles/alidade_smooth.json',
      center: [carWashStations[0].longitude, carWashStations[0].latitude],
      zoom: 13,
    });

    mapInstance.current = map;

    carWashStations.forEach(station => {
      const coordinates = [station.longitude, station.latitude];

      const popup = new maplibregl.Popup().setHTML(
        `<strong>${station.name}</strong><br/>
         ${station.address}<br/>
         <em>Pressão: ${station.pressureBar} bar</em>`
      );

      popupsRef.current[station.id] = popup;

      new maplibregl.Marker({ color: '#1677ff' })
        .setLngLat(coordinates)
        .setPopup(popup)
        .addTo(map);
    });

    return () => map.remove();
  }, [carWashStations]);

  const handleSelect = (station) => {
    setSelectedId(station.id);
    const map = mapInstance.current;
    const popup = popupsRef.current[station.id];

    map.flyTo({
      center: [station.longitude, station.latitude],
      zoom: 15,
      speed: 1.2,
    });

    popup.addTo(map);
  };

  if (isLoading) {
    return <div className={styles.container}>Carregando estações de lavagem...</div>;
  }

  if (error) {
    return (
      <div className={styles.container}>
        <p>Erro ao carregar estações: {error}</p>
        <button onClick={() => window.location.reload()}>Tentar novamente</button>
      </div>
    );
  }

  return (
    <div className={styles.container}>
      <div className={styles.list}>
        <h2>Estações de Lavagem</h2>
        {carWashStations.length === 0 ? (
          <p>Nenhuma estação disponível no momento</p>
        ) : (
          <div>
            {carWashStations.map(station => (
              <div
                key={station.id}
                className={`${styles.card} ${selectedId === station.id ? styles.selected : ''}`}
                onClick={() => handleSelect(station)}
              >
                <h3>{station.name}</h3>
                <p>{station.address}</p>
                <div className={styles.buttonGroup}>
                  <button

                    name="reservar"
                    className={styles.reserveButton}
                    onClick={(e) => {
                      e.stopPropagation();
                      navigate('/reservar', { 
                        state: { 
                          stationId: station.id,  // Passa apenas o ID
                          // Ou se preferir manter os dados completos:
                          stationData: station 
                        } 
                      });
                    }}
                  >
                    Reservar
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
      <div className={styles.map} ref={mapRef}></div>
    </div>
  );
};

export default CarWashMap;
