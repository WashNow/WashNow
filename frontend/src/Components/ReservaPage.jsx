import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import styles from './ReservaPage.module.css';
import { Modal, Radio, Button } from 'antd';

const ReservaPage = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem('user'));

    const [selectedDate, setSelectedDate] = useState('');
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const [errors, setErrors] = useState({});
    const [showPayment, setShowPayment] = useState(false);
    const [paymentMethod, setPaymentMethod] = useState('');
    const [isProcessing, setIsProcessing] = useState(false);
    const [paymentSuccess, setPaymentSuccess] = useState(false);
    const [showSuccessPopup, setShowSuccessPopup] = useState(false);

    const { stationId, stationData } = location.state || {};

    const [stationInfo, setStationInfo] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    const generateTimeSlots = (startHour, endHour, intervalMinutes) => {
        const slots = [];
        const start = startHour * 60;
        const end = endHour * 60;

        for (let minutes = start; minutes <= end; minutes += intervalMinutes) {
            const hours = String(Math.floor(minutes / 60)).padStart(2, '0');
            const mins = String(minutes % 60).padStart(2, '0');
            slots.push(`${hours}:${mins}`);
        }
        return slots;
    };

    useEffect(() => {
        if (!user?.isAuthenticated) {
            navigate('/');
        } else if (user.isOwner) {
            alert('Apenas utilizadores do tipo condutor podem fazer reservas.');
            navigate('/');
        }
    }, []);

    const timeSlots = generateTimeSlots(8, 19, 20);

    const criarReserva = async () => {
        const startISO = new Date(`${selectedDate}T${startTime}:00`).toISOString();
        const endISO = new Date(`${selectedDate}T${endTime}:00`).toISOString();

        const response = await fetch('/api/bookings', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                carwashBayId: stationInfo.id,
                userId: user?.userId,
                startTime: startISO,
                endTime: endISO,
                bookingStatus: "RESERVED"
            }),
        });

        if (!response.ok) {
            throw new Error('Erro ao criar a reserva');
        }
    };


    const validateForm = () => {
        const newErrors = {};

        if (!selectedDate) newErrors.date = 'Por favor, selecione uma data';
        if (!startTime) newErrors.startTime = 'Por favor, selecione a hora de início';
        if (!endTime) newErrors.endTime = 'Por favor, selecione a hora de término';
        if (startTime && endTime && startTime >= endTime) {
            newErrors.timeRange = 'A hora de término deve ser posterior à hora de início';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = () => {
        if (validateForm()) {
            setShowPayment(true);
        }
    };

    useEffect(() => {
        const fetchStationData = async () => {
            try {
                if (stationData) {
                    setStationInfo(stationData);
                    setIsLoading(false);
                } else if (stationId) {
                    const response = await fetch(`/api/CarwashStations/${stationId}`);
                    if (!response.ok) {
                        throw new Error('Estação não encontrada');
                    }

                    const data = await response.json();
                    setStationInfo(data);
                } else {
                    throw new Error('Nenhuma estação especificada');
                }
            } catch (err) {
                console.error('Erro ao buscar dados da estação:', err);
                setError(err.message);
            } finally {
                setIsLoading(false);
            }
        };

        fetchStationData();
    }, [stationId, stationData]);

    const handlePayment = async () => {
        if (!paymentMethod) {
            alert('Por favor, selecione um método de pagamento');
            return;
        }

        setIsProcessing(true);

        try {
            await criarReserva();

            setPaymentSuccess(true);
            setTimeout(() => navigate('/Mapa'), 1000);
        } catch (error) {
            console.error('Erro ao processar pagamento/reserva:', error);
            alert('Ocorreu um erro ao processar a reserva. Tente novamente.');
        } finally {
            setIsProcessing(false);
        }
    };

    const handlePayLater = async () => {
        if (!validateForm()) return;

        try {
            await criarReserva(); // reutiliza a função

            setShowSuccessPopup(true);
            setTimeout(() => navigate('/Perfil'), 1500);
        } catch (error) {
            console.error('Erro ao criar reserva:', error);
            alert('Ocorreu um erro ao criar a reserva. Tente novamente.');
        }
    };


    if (isLoading) return <div className={styles.container}>Carregando informações da estação...</div>;
    if (error) {
        return (
            <div className={styles.container}>
                <p>Erro: {error}</p>
                <button onClick={() => navigate(-1)}>Voltar</button>
            </div>
        );
    }
    if (!stationInfo) return <div className={styles.container}>Estação não encontrada.</div>;

    return (
        <div className={styles.container}>
            <h1>Reserva em {stationInfo.name}</h1>
            <p><strong>Endereço:</strong> {stationInfo.address}</p>

            <div className={styles.formSection}>
                <label>
                    Data:
                    <input
                        type="date"
                        name="date"
                        value={selectedDate}
                        onChange={(e) => {
                            setSelectedDate(e.target.value);
                            setShowPayment(false);
                        }}
                        className={`${styles.input} ${errors.date ? styles.error : ''}`}
                        min={new Date().toISOString().split('T')[0]}
                    />
                    {errors.date && <span className={styles.errorMessage}>{errors.date}</span>}
                </label>

                <label>
                    Hora de Início:
                    <select
                        value={startTime}
                        name="startTime"
                        onChange={(e) => {
                            setStartTime(e.target.value);
                            setShowPayment(false);
                        }}
                        className={`${styles.input} ${errors.startTime ? styles.error : ''}`}
                    >
                        <option value="">Selecionar...</option>
                        {timeSlots.map(time => (
                            <option key={`start-${time}`} value={time}>{time}</option>
                        ))}
                    </select>
                    {errors.startTime && <span className={styles.errorMessage}>{errors.startTime}</span>}
                </label>

                <label>
                    Hora de Término:
                    <select
                        value={endTime}
                        name="endTime"
                        onChange={(e) => {
                            setEndTime(e.target.value);
                            setShowPayment(false);
                        }}
                        className={`${styles.input} ${errors.endTime || errors.timeRange ? styles.error : ''}`}
                    >
                        <option value="">Selecionar...</option>
                        {timeSlots
                            .filter(time => !startTime || time > startTime)
                            .map(time => (
                                <option key={`end-${time}`} value={time}>{time}</option>
                            ))}
                    </select>
                    {errors.endTime && <span className={styles.errorMessage}>{errors.endTime}</span>}
                    {errors.timeRange && <span className={styles.errorMessage}>{errors.timeRange}</span>}
                </label>

                <div className={styles.buttonGroup}>
                    <button className={styles.confirmButton} onClick={handleSubmit}>
                        Continuar para Pagamento
                    </button>
                    <button className={styles.laterButton} onClick={handlePayLater}>
                        Pagar Depois
                    </button>
                </div>
            </div>

            <Modal
                title="Resumo da Reserva"
                open={showPayment}
                onCancel={() => setShowPayment(false)}
                footer={null}
                centered
            >
                <h3>Resumo da Reserva</h3>
                <p><strong>Local:</strong> {stationInfo.name}</p>
                <p><strong>Data:</strong> {selectedDate}</p>
                <p><strong>Horário:</strong> {startTime} - {endTime}</p>
                    
                <div className={styles.paymentMethods}>
                    <h4>Método de Pagamento</h4>
                    <label className={styles.paymentOption}>
                        <input
                            type="radio"
                            name="payment"
                            value="Cartão de Crédito"
                            onChange={() => setPaymentMethod('Cartão de Crédito')}
                        />
                        Cartão de Crédito
                    </label>
                    <label className={styles.paymentOption} id="mbway">
                        <input
                            type="radio"
                            name="payment"
                            value="MB Way"
                            onChange={() => setPaymentMethod('MB Way')}
                        />
                        MB Way
                    </label>
                    <label className={styles.paymentOption}>
                        <input
                            type="radio"
                            name="payment"
                            value="Dinheiro"
                            onChange={() => setPaymentMethod('Dinheiro')}
                        />
                        Dinheiro
                    </label>
                </div>

                    <button 
                    className={styles.payButton}
                    onClick={handlePayment}
                >
                    Confirmar Pagamento
                </button>
            </Modal>

            {(isProcessing || paymentSuccess) && (
                <div className={styles.paymentModal}>
                    <div className={styles.modalContent}>
                        {!paymentSuccess ? (
                            <>
                                <div className={styles.loadingSpinner}></div>
                                <p>A processar pagamento...</p>
                            </>
                        ) : (
                            <>
                                <div className={styles.successCheckmark}>✓</div>
                                <p>Pagamento aceite!</p>
                            </>
                        )}
                    </div>
                </div>
            )}

            {showSuccessPopup && (
                <div className={styles.paymentModal}>
                    <div className={styles.modalContent}>
                        <div className={styles.successCheckmark}>✓</div>
                        <p>Reserva feita com sucesso!</p>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ReservaPage;
