import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { logout } from "../../../context/slices/auth-slices";
import { FiPhone, FiMail, FiLogOut, FiUser } from "react-icons/fi";

const Topbar = () => {
    const { isLoggedIn, user } = useSelector((s) => s.auth);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleLogout = () => {
        dispatch(logout());
        navigate("/login");
    };

    return (
        <div className="topbar">
            <div className="container d-flex justify-content-between align-items-center">
                <div className="d-flex gap-3">
                    <span className="d-flex align-items-center gap-1">
                        <FiPhone size={12} /> +1 (555) 000-0000
                    </span>
                    <span className="d-flex align-items-center gap-1">
                        <FiMail size={12} /> info@campus.edu
                    </span>
                </div>
                <div className="d-flex align-items-center gap-3">
                    {isLoggedIn ? (
                        <>
                            <span className="d-flex align-items-center gap-1">
                                <FiUser size={12} />
                                <strong className="text-white">{user?.name || user?.username}</strong>
                                <span className="badge bg-primary ms-1" style={{ fontSize: ".65rem" }}>
                                    {user?.role?.replace("_", " ")}
                                </span>
                            </span>
                            <button
                                className="btn btn-sm btn-outline-light py-0 d-flex align-items-center gap-1"
                                style={{ fontSize: ".78rem" }}
                                onClick={handleLogout}
                            >
                                <FiLogOut size={12} /> Sign Out
                            </button>
                        </>
                    ) : (
                        <Link to="/login" className="text-white text-decoration-none" style={{ fontSize: ".82rem" }}>
                            Sign In
                        </Link>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Topbar;
