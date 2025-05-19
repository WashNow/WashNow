import React from 'react';
import styles from './Home.module.css';
import { Button } from 'antd';
import car from '../assets/car_wash.png';
import { CarOutlined, SettingOutlined, ClockCircleOutlined } from '@ant-design/icons';

const Home = () => {
  return (
    <div className={styles.home}>
      <section className={styles.hero}>
        <div className={styles.heroContent}>
          <h1>CarWash Tracker</h1>
          <p>
            Digital convenience for car-wash drivers and operators. Reserve bays, unlock jet-washes, and pay seamlessly — all from one intuitive platform.
          </p>
          <div className={styles.buttons}>
            <Button type="primary" size="large">Sou Condutor</Button>
            <Button size="large">Sou Operador</Button>
          </div>
        </div>
        <div className={styles.heroImage}>
          <img src={car}  />
        </div>
      </section>

      <section className={styles.features}>
        <div className={styles.feature}>
          <CarOutlined className={styles.icon} />
          <h3>Reserva Rápida</h3>
          <p>Encontra boxes disponíveis e garante o teu lugar em segundos.</p>
        </div>
        <div className={styles.feature}>
          <ClockCircleOutlined className={styles.icon} />
          <h3>Desbloqueio e Pagamento</h3>
          <p>Desbloqueia a máquina, usa o programa e paga automaticamente.</p>
        </div>
        <div className={styles.feature}>
          <SettingOutlined className={styles.icon} />
          <h3>Painel de Controlo</h3>
          <p>Gestão eficiente de boxes, horários, preços e consumo.</p>
        </div>
      </section>
    </div>
  );
};

export default Home;
