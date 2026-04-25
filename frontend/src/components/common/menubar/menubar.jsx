import React, { useState } from "react";
import { NavLink, Link } from "react-router-dom";
import { useSelector } from "react-redux";
import { FiGrid } from "react-icons/fi";

const links = [
    { to: "/", label: "Home", end: true },
    { to: "/about", label: "About" },
    { to: "/courses", label: "Courses" },
    { to: "/events", label: "Events" },
    { to: "/contact", label: "Contact" },
];

const Menubar = () => {
    const { isLoggedIn } = useSelector((s) => s.auth);
    const [open, setOpen] = useState(false);

    return (
        <nav className="navbar navbar-expand-lg main-navbar">
            <div className="container">
                <Link className="navbar-brand d-flex align-items-center gap-2" to="/">
                    <div
                        style={{
                            width: 32, height: 32, borderRadius: 8,
                            background: "rgba(255,255,255,.2)",
                            display: "flex", alignItems: "center", justifyContent: "center",
                            fontWeight: 700, fontSize: "1rem",
                        }}
                    >S</div>
                    CampusMS
                </Link>

                <button
                    className="navbar-toggler border-0"
                    onClick={() => setOpen(!open)}
                    aria-label="Toggle navigation"
                >
                    <span className="navbar-toggler-icon" style={{ filter: "invert(1)" }} />
                </button>

                <div className={`collapse navbar-collapse ${open ? "show" : ""}`}>
                    <ul className="navbar-nav me-auto">
                        {links.map(({ to, label, end }) => (
                            <li className="nav-item" key={to}>
                                <NavLink
                                    to={to}
                                    end={end}
                                    className={({ isActive }) =>
                                        "nav-link" + (isActive ? " active" : "")
                                    }
                                    onClick={() => setOpen(false)}
                                >
                                    {label}
                                </NavLink>
                            </li>
                        ))}
                    </ul>
                    <ul className="navbar-nav ms-auto">
                        {isLoggedIn ? (
                            <li className="nav-item">
                                <NavLink
                                    to="/dashboard"
                                    className={({ isActive }) =>
                                        "nav-link d-flex align-items-center gap-1" + (isActive ? " active" : "")
                                    }
                                    onClick={() => setOpen(false)}
                                >
                                    <FiGrid size={14} /> Dashboard
                                </NavLink>
                            </li>
                        ) : (
                            <li className="nav-item">
                                <Link
                                    to="/login"
                                    className="btn btn-sm ms-2"
                                    style={{
                                        background: "rgba(255,255,255,.15)",
                                        color: "#fff",
                                        border: "1px solid rgba(255,255,255,.3)",
                                        borderRadius: ".4rem",
                                        fontWeight: 500,
                                    }}
                                    onClick={() => setOpen(false)}
                                >
                                    Sign In
                                </Link>
                            </li>
                        )}
                    </ul>
                </div>
            </div>
        </nav>
    );
};

export default Menubar;
