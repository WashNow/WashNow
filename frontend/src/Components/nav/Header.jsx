import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import styles from './Header.module.css';
import profilePic from '../../assets/profile.png';

const Header = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const goTo = (path) => {
        navigate(path);
    };

    const handleLogout = () => {
        navigate('/');
    };

    return (
        <>
            <header className={styles.header}>
                <div className={styles.logo}>WashNow</div>
                <nav className={styles.nav}>
                    <div
                        onClick={() => goTo('/Mapa')}
                        className={`${styles.navItem} ${location.pathname === '/Mapa' ? styles.active : ''}`}
                    >
                        <span>Mapa</span>
                    </div>
                    <div
                        onClick={() => goTo('/Perfil')}
                        className={`${styles.navItem} ${location.pathname === '/Perfil' ? styles.active : ''}`}
                    >
                        <div className={styles.avatarWrapper}>
                            <img src={profilePic} alt="Perfil" className={styles.avatar} />
                        </div>
                        <span>Perfil</span>
                    </div>
                    <div
                        onClick={handleLogout}
                        className={styles.navItemLogout}
                    >
                        <span>Logout</span>
                    </div>
                </nav>
            </header>
            <Outlet />
        </>
    );
};

export default Header;
