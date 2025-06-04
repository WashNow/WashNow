import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import styles from './LoginPage.module.css';
import HeaderLogin from '../Components/nav/HeaderLogin';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    if (!email) {
      setErrorMessage('Por favor, preencha o email.');
      return;
    }

    try {
      const res = await fetch('/api/Persons');
      if (!res.ok) throw new Error('Erro ao buscar utilizadores');

      const users = await res.json();
      const foundUser = users.find(user => user.email.toLowerCase() === email.toLowerCase());

      if (foundUser) {
        setErrorMessage('');
        navigate('/Mapa');
      } else {
        setErrorMessage('Email não encontrado.');
      }
    } catch (err) {
      console.error(err);
      setErrorMessage('Erro ao fazer login. Tente novamente.');
    }
  };

  return (
    <div>
      <HeaderLogin />
      <div className={styles.container}>
        <form className={styles.form} onSubmit={handleLogin}>
          <h2>Login</h2>

          <input
            type="email"
            name="email"
            placeholder="Email"
            className={styles.input}
            value={email}
            onChange={(e) => {
              setEmail(e.target.value);
              setErrorMessage('');
            }}
          />
          {errorMessage && <p className={styles.error}>{errorMessage}</p>}

          <button type="submit" className={styles.button}>Entrar</button>

          <p className={styles.linkText}>
            Ainda não tem conta? <Link to="/signup" className={styles.link}>Criar conta</Link>
          </p>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;
