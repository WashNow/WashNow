import React from 'react';
import styles from './Perfil.module.css';
import profilePic from '../assets/profile.png';

const userInfo = {
    name: 'João Silva',
    email: 'joao.silva@email.com',
};

const bookingHistory = [
    {
        id: 1,
        date: '2025-05-10',
        location: 'Lava Rápido Aveiro',
        status: 'Concluído',
    },
    {
        id: 2,
        date: '2025-05-05',
        location: 'Auto Lavagem Central',
        status: 'Cancelado',
    },
    {
        id: 3,
        date: '2025-04-30',
        location: 'Lava Rápido Aveiro',
        status: 'Concluído',
    },
    {
        id: 4,
        date: '2025-04-30',
        location: 'Lava Rápido Aveiro',
        status: 'Concluído',
    },
    {
        id: 5,
        date: '2025-04-30',
        location: 'Lava Rápido Aveiro',
        status: 'Concluído',
    },
];

const Perfil = () => {
    return (
        <div className={styles.container}>
            <div className={styles.profileHeader}>
                <img src={profilePic} alt="Perfil" className={styles.profilePic} />
                <h1>{userInfo.name}</h1>
                <p className={styles.email}>{userInfo.email}</p>
            </div>
            <div className={styles.history}>
                <h2>Histórico de Reservas</h2>
                <ul>
                    {bookingHistory.map(res => (
                        <li key={res.id} className={styles.reserva}>
                            <div>
                                <strong>{res.location}</strong>
                                <p>{res.date}</p>
                            </div>
                            <span
                                className={`${styles.status} ${res.status === 'Concluído'
                                    ? styles.done
                                    : styles.cancelled
                                    }`}
                            >
                                {res.status}
                            </span>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default Perfil;
