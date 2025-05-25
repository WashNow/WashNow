import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './OwnerHome.module.css';

const OwnerHome = ({ ownerId }) => {
  const [stations, setStations] = useState([]);
  const [bays, setBays] = useState([]);
  const [reservations, setReservations] = useState([]);
  const [selectedStationId, setSelectedStationId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchStations = async () => {
      try {
        const res = await fetch('/api/CarwashStations');
        const data = await res.json();
        const ownerStations = Array.isArray(data)
          ? data.filter(station => station.ownerId === ownerId)
          : [];
        setStations(ownerStations);
        if (ownerStations.length > 0) setSelectedStationId(ownerStations[0].id);
      } catch (err) {
        console.error('Erro ao buscar estações:', err);
      }
    };
    fetchStations();
  }, [ownerId]);

  useEffect(() => {
    const fetchBays = async () => {
      try {
        const res = await fetch('/api/bookings');
        const data = await res.json();
        const bayList = Array.isArray(data) ? data : [];
        setBays(bayList);
        console.log('Baias carregadas:', bayList);
      } catch (err) {
        console.error('Erro ao buscar baias:', err);
      }
    };
    fetchBays();
  }, []);

  useEffect(() => {
    const fetchReservations = async () => {
      try {
        const res = await fetch('/api/bookings');
        const bookingsData = await res.json();

        if (!selectedStationId) {
          setReservations([]);
          return;
        }

        const filtered = bookingsData.filter(
          booking => booking.carwashBayId === selectedStationId
        );

        const uniqueUserIds = [...new Set(filtered.map(b => b.userId))];

        const userMap = {};
        await Promise.all(
          uniqueUserIds.map(async (id) => {
            const res = await fetch(`/api/Persons/${id}`);
            const userData = await res.json();
            userMap[id] = userData.name;
          })
        );

        const bookingsWithNames = filtered.map(b => ({
          ...b,
          userName: userMap[b.userId] || `Utilizador #${b.userId}`,
        }));

        setReservations(bookingsWithNames);
      } catch (err) {
        console.error('Erro ao buscar reservas:', err);
      }
    };

    fetchReservations();
  }, [selectedStationId]);



  return (
    <div className={styles.container}>
      <div className={styles.sidebar}>
        <button className={styles.addButton} onClick={() => navigate('/add-station')}>
          + Adicionar Estação
        </button>
        <h2>Minhas Estações</h2>
        {stations.length === 0 && <p>Nenhuma estação encontrada.</p>}
        {stations.map((station) => (
          <div
            key={station.id}
            className={`${styles.stationCard} ${station.id === selectedStationId ? styles.selected : ''}`}
            onClick={() => setSelectedStationId(station.id)}
          >
            <h3>{station.name}</h3>
            <p>{station.address}</p>
          </div>
        ))}
      </div>
      <div className={styles.mainContent}>
        <h2>Reservas</h2>
        {reservations.length === 0 ? (
          <p>Nenhuma reserva encontrada para esta estação.</p>
        ) : (
          <ul className={styles.reservationList}>
            {reservations.map((res) => (
              <li key={res.id} className={styles.reservationCard}>
                <p><strong>Cliente:</strong> {res.userName}</p>
                <p><strong>Início:</strong> {new Date(res.startTime).toLocaleString()}</p>
                <p><strong>Fim:</strong> {new Date(res.endTime).toLocaleString()}</p>
                <p><strong>Estado:</strong> {res.bookingStatus}</p>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default OwnerHome;
