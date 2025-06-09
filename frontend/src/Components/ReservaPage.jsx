import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './ReservaPage.module.css';
import { Modal } from 'antd';

const ReservaPage = () => {
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem('user'));

    const [stations, setStations] = useState([]);
    const [bays, setBays] = useState([]);
    const [selectedStationId, setSelectedStationId] = useState('');
    const [selectedBayId, setSelectedBayId] = useState('');
    const [selectedDate, setSelectedDate] = useState('');
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const [paymentMethod, setPaymentMethod] = useState('');
    const [errors, setErrors] = useState({});
    const [showPayment, setShowPayment] = useState(false);
    const [isProcessing, setIsProcessing] = useState(false);
    const [paymentSuccess, setPaymentSuccess] = useState(false);
    const [showSuccessPopup, setShowSuccessPopup] = useState(false);

    useEffect(() => {
        if (!user?.isAuthenticated) {
            navigate('/');
        } else if (user.isOwner) {
            alert('Apenas utilizadores do tipo condutor podem fazer reservas.');
            navigate('/');
        }
    }, [navigate, user?.isAuthenticated, user?.isOwner]);

    useEffect(() => {
        const fetchStations = async () => {
            try {
                const res = await fetch('/api/CarwashStations');
                const data = await res.json();
                setStations(data);
            } catch (err) {
                console.error('Erro ao buscar estações:', err);
            }
        };
        fetchStations();
    }, []);

    useEffect(() => {
        const fetchBays = async () => {
            if (!selectedStationId) return;
            try {
                const res = await fetch(`/api/CarwashBays?stationId=${selectedStationId}`);
                const data = await res.json();
                setBays(data);
            } catch (err) {
                console.error('Erro ao buscar baias:', err);
            }
        };
        fetchBays();
    }, [selectedStationId]);

    const criarReserva = async () => {
        if (!selectedDate || !startTime || !endTime || !paymentMethod) {
            console.error('Campos inválidos:', { selectedDate, startTime, endTime, paymentMethod });
            alert('Por favor, preencha todos os campos de data, horário e método de pagamento.');
            return;
        }

        try {
            const startISO = new Date(`${selectedDate}T${startTime}:00`).toISOString();
            const endISO = new Date(`${selectedDate}T${endTime}:00`).toISOString();

            const response = await fetch('/api/bookings', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    carwashBayId: selectedBayId,
                    userId: user?.userId,
                    startTime: startISO,
                    endTime: endISO,
                    bookingStatus: "RESERVED",
                    paymentMethod: paymentMethod, // Inclui o método de pagamento
                }),
            });

            if (!response.ok) {
                throw new Error('Erro ao criar a reserva');
            }
        } catch (error) {
            console.error('Erro ao criar reserva:', error);
            alert('Ocorreu um erro ao criar a reserva. Tente novamente.');
        }
    };

    const validateForm = () => {
        const newErrors = {};

        if (!selectedStationId) newErrors.station = 'Por favor, selecione uma estação';
        if (!selectedBayId) newErrors.bay = 'Por favor, selecione uma baia';
        if (!selectedDate) newErrors.date = 'Por favor, selecione uma data';
        if (!startTime) newErrors.startTime = 'Por favor, selecione a hora de início';
        if (!endTime) newErrors.endTime = 'Por favor, selecione a hora de término';
        if (startTime && endTime && startTime >= endTime) {
            newErrors.timeRange = 'A hora de término deve ser posterior à hora de início';
        }
        if (!paymentMethod) newErrors.paymentMethod = 'Por favor, selecione um método de pagamento';

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = () => {
        if (validateForm()) {
            setShowPayment(true);
        }
    };

    const handlePayment = async () => {
        if (!validateForm()) return;

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
            await criarReserva();

            setShowSuccessPopup(true);
            setTimeout(() => navigate('/Perfil'), 1500);
        } catch (error) {
            console.error('Erro ao criar reserva:', error);
            alert('Ocorreu um erro ao criar a reserva. Tente novamente.');
        }
    };

    const timeSlots = [];
    for (let h = 8; h < 20; h++) {
        timeSlots.push(`${String(h).padStart(2, '0')}:00`);
        timeSlots.push(`${String(h).padStart(2, '0')}:30`);
    }

    return (
        <div className={styles.container}>
            <h1>Reserva</h1>

            <div className={styles.formSection}>
                <label>
                    Estação:
                    <select
                        value={selectedStationId}
                        onChange={(e) => setSelectedStationId(e.target.value)}
                        className={`${styles.input} ${errors.station ? styles.error : ''}`}
                    >
                        <option value="">Selecionar...</option>
                        {stations.map(station => (
                            <option key={station.id} value={station.id}>
                                {station.name}
                            </option>
                        ))}
                    </select>
                    {errors.station && <span className={styles.errorMessage}>{errors.station}</span>}
                </label>

                <label>
                    Baia:
                    <select
                        value={selectedBayId}
                        onChange={(e) => setSelectedBayId(e.target.value)}
                        className={`${styles.input} ${errors.bay ? styles.error : ''}`}
                    >
                        <option value="">Selecionar...</option>
                        {bays.map(bay => (
                            <option key={bay.id} value={bay.id}>
                                {bay.identifiableName}
                            </option>
                        ))}
                    </select>
                    {errors.bay && <span className={styles.errorMessage}>{errors.bay}</span>}
                </label>

                <label>
                    Data:
                    <input
                        type="date"
                        value={selectedDate}
                        onChange={(e) => setSelectedDate(e.target.value)}
                        className={`${styles.input} ${errors.date ? styles.error : ''}`}
                        min={new Date().toISOString().split('T')[0]}
                    />
                    {errors.date && <span className={styles.errorMessage}>{errors.date}</span>}
                </label>

                <label>
                    Hora de Início:
                    <select
                        value={startTime}
                        onChange={(e) => setStartTime(e.target.value)}
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
                        onChange={(e) => setEndTime(e.target.value)}
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

                <label>
                    Método de Pagamento:
                    <select
                        value={paymentMethod}
                        onChange={(e) => setPaymentMethod(e.target.value)}
                        className={`${styles.input} ${errors.paymentMethod ? styles.error : ''}`}
                    >
                        <option value="">Selecionar...</option>
                        <option value="CREDIT_CARD">Cartão de Crédito</option>
                        <option value="PAYPAL">PayPal</option>
                        <option value="MBWAY">MB Way</option>
                    </select>
                    {errors.paymentMethod && <span className={styles.errorMessage}>{errors.paymentMethod}</span>}
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
                <p><strong>Estação:</strong> {stations.find(s => s.id === selectedStationId)?.name}</p>
                <p><strong>Baia:</strong> {bays.find(b => b.id === selectedBayId)?.identifiableName}</p>
                <p><strong>Data:</strong> {selectedDate}</p>
                <p><strong>Horário:</strong> {startTime} - {endTime}</p>
                    
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