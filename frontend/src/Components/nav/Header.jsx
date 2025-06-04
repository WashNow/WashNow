import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import styles from './Header.module.css';
import profilePic from '../../assets/profile.png';
import { useEffect } from 'react';

const Header = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem('user'));

    const goTo = (path) => {
        navigate(path);
    };

    const handleLogout = () => {
        localStorage.removeItem('user');
        navigate('/');
    };

    useEffect(() => {
        if (!user?.isAuthenticated && location.pathname !== '/') {
            navigate('/');
        }
    }, [location.pathname, navigate, user]);

    if (!user?.isAuthenticated) return null;

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
                    {user?.isOwner && (
                        <div
                            onClick={() => goTo('/OwnerHome')}
                            className={`${styles.navItem} ${location.pathname === '/OwnerHome' ? styles.active : ''}`}
                        >
                            <span>Painel Dono</span>
                        </div>
                    )}
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