import { NavLink } from 'react-router-dom';
import '../styles/Navbar.css';

function Navbar() {
    return (
        <nav className="navbar">
            <div className="navbar-inner">
                <span className="navbar-logo">Car Rentals Service</span>
                <div className="navbar-links">
                    <NavLink to="/" end className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
                        Search
                    </NavLink>
                    <NavLink to="/modify" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
                        Modify
                    </NavLink>
                    <NavLink to="/cancel" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
                        Cancel
                    </NavLink>
                </div>
            </div>
        </nav>
    );
}

export default Navbar;