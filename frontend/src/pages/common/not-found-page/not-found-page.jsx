import React from "react";
import { Link } from "react-router-dom";
import { FiArrowLeft } from "react-icons/fi";

const NotFoundPage = () => (
    <div className="d-flex flex-column align-items-center justify-content-center" style={{ minHeight: "70vh" }}>
        <div style={{ fontSize: "6rem", fontWeight: 800, color: "#e2e8f0" }}>404</div>
        <h3 className="fw-bold mb-2" style={{ color: "#0f172a" }}>Page Not Found</h3>
        <p className="text-muted mb-4">The page you're looking for doesn't exist or has been moved.</p>
        <Link to="/" className="btn btn-primary d-flex align-items-center gap-2">
            <FiArrowLeft size={15} /> Back to Home
        </Link>
    </div>
);

export default NotFoundPage;
