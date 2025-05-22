import { HomeTwoTone, EditTwoTone, CheckCircleTwoTone } from '@ant-design/icons';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import styles from './Header.module.css';
import profilePic from '../../assets/profile.png';


const Header = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const goTo = (path) => {
        navigate(path);
    };

    return (
        <>
            <header className={styles.header}>
                <div className={styles.logo}>WashNow</div>
                <nav className={styles.nav}>
                    <div
                        onClick={() => goTo('/')}
                        className={`${styles.navItem} ${location.pathname === '/' ? styles.active : ''}`}
                    >
                        <HomeTwoTone />
                        <span>Home</span>
                    </div>
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

                </nav>
            </header>
            <Outlet />
        </>
    );
};

export default Header;
