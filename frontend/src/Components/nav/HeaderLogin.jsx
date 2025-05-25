import { HomeTwoTone, EditTwoTone, CheckCircleTwoTone } from '@ant-design/icons';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import styles from './Header.module.css';
import profilePic from '../../assets/profile.png';


const HeaderLogin = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const goTo = (path) => {
        navigate(path);
    };

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
