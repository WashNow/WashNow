import React, { useEffect, useState } from 'react';
import styles from './Perfil.module.css';
import profilePic from '../assets/profile.png';
import Header from '../Components/nav/Header';

const Perfil = () => {
    const [userInfo, setUserInfo] = useState(null);
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const statusMap = {
        RESERVED: 'Reservado',
        IN_PROGRESS: 'Em progresso',
        WASHING_COMPLETED: 'Lavagem concluída',
        CANCELED: 'Cancelado',
    };

useEffect(() => {
    const fetchUserAndBookings = async () => {
        try {
            const localUser = JSON.parse(localStorage.getItem('user'));
            if (!localUser?.userId) throw new Error('Utilizador não autenticado');

            const userId = localUser.userId;

            // Buscar pessoa pelo ID
            const personsRes = await fetch('/api/Persons');
            if (!personsRes.ok) throw new Error('Erro ao buscar utilizadores');

            const persons = await personsRes.json();
            const user = persons.find(p => p.id === userId);
            if (!user) throw new Error('Utilizador não encontrado');

            setUserInfo(user);

            // Buscar todas as reservas
            const bookingsRes = await fetch('/api/bookings');
            if (!bookingsRes.ok) throw new Error('Erro ao buscar reservas');

            const allBookings = await bookingsRes.json();
            const userBookings = allBookings.filter(b => b.userId === userId);

            // Buscar info das estações associadas
            const bookingsWithStationInfo = await Promise.all(
                userBookings.map(async booking => {
                    try {
                        const stationRes = await fetch(`/api/CarwashStations/${booking.carwashBayId}`);
                        if (!stationRes.ok) throw new Error();

                        const stationData = await stationRes.json();
                        return {
                            ...booking,
                            stationName: stationData.name,
                        };
                    } catch {
                        return {
                            ...booking,
                            stationName: 'Estação desconhecida',
                        };
                    }
                })
            );

            setBookings(bookingsWithStationInfo);
        } catch (err) {
            console.error(err);
            setError('Não foi possível carregar os dados do utilizador ou das reservas.');
        } finally {
            setLoading(false);
        }
    };

    fetchUserAndBookings();
}, []);


    return (
        <div>
            <Header />

            <div className={styles.container}>
                {userInfo ? (
                    <div className={styles.profileHeader}>
                        <img src={profilePic} alt="Perfil" className={styles.profilePic} />
                        <h1>{userInfo.name}</h1>
                        <p className={styles.email}>{userInfo.email}</p>
                    </div>
                ) : (
                    <p>A carregar informações do utilizador...</p>
                )}

                <div className={styles.history}>
                    <h2>Histórico de Reservas</h2>

                    {loading ? (
                        <p>A carregar reservas...</p>
                    ) : error ? (
                        <p>{error}</p>
                    ) : bookings.length === 0 ? (
                        <p>Não há reservas disponíveis.</p>
                    ) : (
                        <ul>
                            {bookings.map(res => (
                                <li key={res.id} className={styles.reserva}>
                                    <div>
                                        <strong>{res.stationName}</strong>
                                        <p>{new Date(res.startTime).toLocaleDateString()}</p>
                                    </div>
                                    <span
                                        className={`${styles.status} ${res.bookingStatus === 'WASHING_COMPLETED' ? styles.completed :
                                            res.bookingStatus === 'IN_PROGRESS' ? styles.inProgress :
                                                res.bookingStatus === 'CANCELED' ? styles.canceled :
                                                    styles.reserved
                                            }`}
                                    >
                                        {statusMap[res.bookingStatus] || res.bookingStatus}
                                    </span>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Perfil;
