import { Outlet } from 'react-router-dom';
import styles from './Header.module.css';

const HeaderLogin = () => {
    return (
        <>
            <header className={styles.header}>
                <div className={styles.logo}>WashNow</div>
            </header>
            <Outlet />
        </>
    );
};

export default HeaderLogin;