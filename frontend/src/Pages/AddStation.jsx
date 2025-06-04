import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import styles from './AddStation.module.css';

const AddStation = () => {
  const [name, setName] = useState('');
  const [address, setAddress] = useState('');
  const [latitude, setLatitude] = useState('');
  const [longitude, setLongitude] = useState('');
  const [showSuccess, setShowSuccess] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();
  const ownerId = location.state?.ownerId ?? 1;

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch('/api/CarwashStations', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, address, latitude, longitude, ownerId }),
      });
      if (res.ok) {
        setShowSuccess(true);
        setTimeout(() => {
          navigate('/ownerhome');
        }, 2000); // espera 2s antes de redirecionar
      } else {
        // opcional: tratar erro e mostrar mensagem
      }
    } catch (err) {
      console.error('Erro ao adicionar estação:', err);
    }
  };

  return (
    <div>
    <Header />
    <div className={styles.container}>
      <h1 className={styles.title}>Adicionar Estação</h1>
      <form onSubmit={handleSubmit} className={styles.form}>
        <label className={styles.label}>Nome</label>
        <input
          className={styles.input}
          type="text"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <label className={styles.label}>Endereço</label>
        <input
          className={styles.input}
          type="text"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
          required
        />
        <label className={styles.label}>Latitude</label>
        <input
          className={styles.input}
          type="number"
          step="any"
          value={latitude}
          onChange={(e) => setLatitude(e.target.value)}
          required
        />
        <label className={styles.label}>Longitude</label>
        <input
          className={styles.input}
          type="number"
          step="any"
          value={longitude}
          onChange={(e) => setLongitude(e.target.value)}
          required
        />
        <button type="submit" className={styles.button}>Guardar</button>
      </form>

      {showSuccess && (
        <div className={styles.successModal}>
          <p>Estação adicionada com sucesso! A redirecionar...</p>
        </div>
      )}
    </div>
    
    </div>
  );
};

export default AddStation;
