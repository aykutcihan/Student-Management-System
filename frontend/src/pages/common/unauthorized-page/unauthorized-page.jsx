import React from "react";
import { Link } from "react-router-dom";
import { FiLock, FiArrowLeft } from "react-icons/fi";

const UnauthorizedPage = () => (
    <div className="d-flex flex-column align-items-center justify-content-center" style={{ minHeight: "70vh" }}>
        <div style={{ width: 80, height: 80, borderRadius: "50%", background: "#fef2f2", display: "flex", alignItems: "center", justifyContent: "center", marginBottom: "1.5rem" }}>
            <FiLock size={36} color="#dc2626" />
        </div>
        <h3 className="fw-bold mb-2" style={{ color: "#0f172a" }}>Access Denied</h3>
        <p className="text-muted mb-4">You don't have permission to access this page.</p>
        <div className="d-flex gap-2">
            <Link to="/" className="btn btn-outline-secondary d-flex align-items-center gap-2"><FiArrowLeft size={15}/> Home</Link>
            <Link to="/dashboard" className="btn btn-primary">Go to Dashboard</Link>
        </div>
    </div>
);

export default UnauthorizedPage;
