import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './OwnerHome.module.css';

const OwnerHome = ({ ownerId }) => {
  const [stations, setStations] = useState([]);
  const [bays, setBays] = useState([]);
  const [reservations, setReservations] = useState([]);
  const [selectedStationId, setSelectedStationId] = useState(null);
  const [selectedBayId, setSelectedBayId] = useState(null);
  const [newBayName, setNewBayName] = useState('');
  const [newBayPrice, setNewBayPrice] = useState('');
  const [showSuccess, setShowSuccess] = useState(false);
  const [showModal, setShowModal] = useState(false);
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
        const res = await fetch('/api/CarwashBays');
        const data = await res.json();
        setBays(Array.isArray(data) ? data : []);
      } catch (err) {
        console.error('Erro ao buscar baias:', err);
      }
    };
    fetchBays();
  }, []);

  const handleAddBay = async (e) => {
    e.preventDefault();
    if (!selectedStationId || !newBayName || !newBayPrice) {
      alert('Por favor, preencha todos os campos.');
      return;
    }

    try {
      const res = await fetch('/api/CarwashBays', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          carwashStation: { id: selectedStationId },
          identifiableName: newBayName,
          pricePerMinute: parseFloat(newBayPrice),
          active: true,
        }),
      });

      if (res.ok) {
        setShowSuccess(true);
        setTimeout(() => setShowSuccess(false), 3000);
        setNewBayName('');
        setNewBayPrice('');
        setShowModal(false);
        const updated = await fetch('/api/CarwashBays');
        const baysData = await updated.json();
        setBays(baysData);
      } else {
        const errData = await res.json();
        alert('Erro ao adicionar baia: ' + (errData.message || 'Erro desconhecido.'));
      }
    } catch (err) {
      console.error('Erro ao adicionar baia:', err);
    }
  };

  const handleShowReservations = async (bayId) => {
    try {
      const res = await fetch(`/api/bookings?bayId=${bayId}`);
      const data = await res.json();
      setReservations(Array.isArray(data) ? data : []);
      setSelectedBayId(bayId);
    } catch (err) {
      console.error('Erro ao buscar reservas:', err);
    }
  };

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
        <h2>Baias da Estação</h2>
        <button onClick={() => setShowModal(true)} className={styles.button}>+ Nova Baia</button>

        {bays.filter(b => b.carwashStation?.id === selectedStationId).length === 0 ? (
          <p>Sem baias nesta estação.</p>
        ) : (
          <ul className={styles.bayList}>
            {bays
              .filter(b => b.carwashStation?.id === selectedStationId)
              .map(bay => (
                <li key={bay.id} className={styles.bayCard}>
                  <p><strong>Nome:</strong> {bay.identifiableName}</p>
                  <p><strong>Preço/min:</strong> €{bay.pricePerMinute}</p>
                  <p><strong>Ativa:</strong> {bay.active ? 'Sim' : 'Não'}</p>
                  <button
                    className={styles.button}
                    onClick={() => handleShowReservations(bay.id)}
                  >
                    Mostrar Reservas
                  </button>
                </li>
              ))}
          </ul>
        )}

        {reservations.length > 0 && selectedBayId && (
          <div className={styles.reservations}>
            <h3>Reservas da Baia</h3>
            <ul>
              {reservations.map(reservation => (
                <li key={reservation.id}>
                  <p><strong>ID:</strong> {reservation.id}</p>
                  <p><strong>Usuário:</strong> {reservation.userId}</p>
                  <p><strong>Início:</strong> {new Date(reservation.startTime).toLocaleString()}</p>
                  <p><strong>Término:</strong> {new Date(reservation.endTime).toLocaleString()}</p>
                  <p><strong>Status:</strong> {reservation.bookingStatus}</p>
                </li>
              ))}
            </ul>
          </div>
        )}

        {showModal && (
          <div className={styles.modalOverlay}>
            <div className={styles.modalContent}>
              <button className={styles.closeButton} onClick={() => setShowModal(false)}>×</button>
              <h3>Adicionar Baia</h3>
              <form onSubmit={handleAddBay} className={styles.form}>
                <label className={styles.label}>Nome da Baia</label>
                <input
                  className={styles.input}
                  type="text"
                  value={newBayName}
                  onChange={(e) => setNewBayName(e.target.value)}
                  required
                />
                <label className={styles.label}>Preço por Minuto (€)</label>
                <input
                  className={styles.input}
                  type="number"
                  step="0.01"
                  value={newBayPrice}
                  onChange={(e) => setNewBayPrice(e.target.value)}
                  required
                />
                <button type="submit" className={styles.button}>Adicionar</button>
              </form>
            </div>
          </div>
        )}

        {showSuccess && <p className={styles.successMessage}>Baia adicionada com sucesso!</p>}
      </div>
    </div>
  );
};

export default OwnerHome;