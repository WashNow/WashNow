import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './SignUpPage.module.css';
import HeaderLogin from '../Components/nav/HeaderLogin';

const SignupPage = () => {
  const [userData, setUserData] = useState({
    name: '',
    email: '',
    type: '',
  });

  const [stationData, setStationData] = useState({
    name: '',
    address: '',
    latitude: '',
    longitude: '',
  });

  const navigate = useNavigate();

  const handleUserChange = (e) => {
    setUserData({ ...userData, [e.target.name]: e.target.value });
  };

  const handleStationChange = (e) => {
    setStationData({ ...stationData, [e.target.name]: e.target.value });
  };
  const handleSignup = async (e) => {
    e.preventDefault();

    try {
      const userPayload = {
        name: userData.name,
        email: userData.email,
        role: userData.type,
      };

      const userRes = await fetch('/api/Persons', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userPayload),
      });

      if (!userRes.ok) {
        const errorText = await userRes.text();
        console.error('Erro ao criar utilizador:', errorText);
        throw new Error('Erro ao criar utilizador');
      }

      const newUser = await userRes.json();

      if (userData.type === 'OWNER') {
        const stationRes = await fetch('/api/CarwashStations', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            ...stationData,
            latitude: parseFloat(stationData.latitude),
            longitude: parseFloat(stationData.longitude),
            ownerID: newUser.id,
          }),
        });

        if (!stationRes.ok) throw new Error('Erro ao criar estação');
      }

      navigate('/');
    } catch (err) {
      alert(err.message);
    }
  };


  return (
    <div>
      <HeaderLogin />
      <div className={styles.container}>
        <form className={styles.form} onSubmit={handleSignup}>
          <h2>Criar Conta</h2>
          <input
            type="text"
            name="name"
            placeholder="Nome"
            value={userData.name}
            onChange={handleUserChange}
            className={styles.input}
            required
          />
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={userData.email}
            onChange={handleUserChange}
            className={styles.input}
            required
          />

          <select
            name="type"
            value={userData.type}
            onChange={handleUserChange}
            className={styles.input}
          >
            <option value="" disabled>Seleciona o tipo de conta</option>
            <option value="DRIVER">Cliente</option>
            <option value="OWNER">Dono de Estação</option>
          </select>

          {userData.type === 'OWNER' && (
            <div className={styles.stationSection}>
              <h3>Informações da Estação</h3>
              <input
                type="text"
                name="name"
                placeholder="Nome da Estação"
                value={stationData.name}
                onChange={handleStationChange}
                className={styles.input}
                required
              />
              <input
                type="text"
                name="address"
                placeholder="Morada"
                value={stationData.address}
                onChange={handleStationChange}
                className={styles.input}
                required
              />
              <input
                type="number"
                name="latitude"
                placeholder="Latitude"
                value={stationData.latitude}
                onChange={handleStationChange}
                className={styles.input}
                step="any"
                required
              />
              <input
                type="number"
                name="longitude"
                placeholder="Longitude"
                value={stationData.longitude}
                onChange={handleStationChange}
                className={styles.input}
                step="any"
                required
              />
            </div>
          )}

          <button type="submit" name="criarBtn" className={styles.button}>Criar Conta</button>
        </form>
      </div>
    </div>
  );
};

export default SignupPage;
