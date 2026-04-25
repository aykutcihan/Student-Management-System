import React from "react";
import { Link, NavLink } from "react-router-dom";
import { useSelector } from "react-redux";

const navLinks = [
    { to: "/", label: "Ana Sayfa" },
    { to: "/about", label: "Hakkımızda" },
    { to: "/courses", label: "Dersler" },
    { to: "/events", label: "Etkinlikler" },
    { to: "/contact", label: "İletişim" },
];

const Menubar = () => {
    const { isLoggedIn } = useSelector((state) => state.auth);

    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
            <div className="container">
                <Link className="navbar-brand fw-bold" to="/">
                    SMS
                </Link>
                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#mainNav"
                >
                    <span className="navbar-toggler-icon" />
                </button>
                <div className="collapse navbar-collapse" id="mainNav">
                    <ul className="navbar-nav me-auto">
                        {navLinks.map(({ to, label }) => (
                            <li className="nav-item" key={to}>
                                <NavLink
                                    to={to}
                                    end={to === "/"}
                                    className={({ isActive }) =>
                                        "nav-link" + (isActive ? " active fw-semibold" : "")
                                    }
                                >
                                    {label}
                                </NavLink>
                            </li>
                        ))}
                    </ul>
                    <ul className="navbar-nav ms-auto">
                        {isLoggedIn ? (
                            <li className="nav-item">
                                <NavLink to="/dashboard" className="nav-link">
                                    Dashboard
                                </NavLink>
                            </li>
                        ) : (
                            <li className="nav-item">
                                <NavLink to="/login" className="nav-link">
                                    Giriş Yap
                                </NavLink>
                            </li>
                        )}
                    </ul>
                </div>
            </div>
        </nav>
    );
};

export default Menubar;
