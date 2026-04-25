import React from "react";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { logout } from "../../../context/slices/auth-slices";
import { useNavigate } from "react-router-dom";

const Topbar = () => {
    const { isLoggedIn, user } = useSelector((state) => state.auth);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleLogout = () => {
        dispatch(logout());
        navigate("/login");
    };

    return (
        <div className="bg-dark text-white py-1 px-3 d-flex justify-content-between align-items-center small">
            <span>School Management System</span>
            <div>
                {isLoggedIn ? (
                    <span>
                        Hoşgeldin, <strong>{user?.name || user?.username}</strong>{" "}
                        &nbsp;|&nbsp;
                        <button
                            className="btn btn-sm btn-outline-light py-0"
                            onClick={handleLogout}
                        >
                            Çıkış
                        </button>
                    </span>
                ) : (
                    <Link to="/login" className="text-white text-decoration-none">
                        Giriş Yap
                    </Link>
                )}
            </div>
        </div>
    );
};

export default Topbar;
