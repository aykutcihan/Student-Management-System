import React from "react";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";

const allCards = [
    { role: ["ADMIN"], to: "/dashboard/admin-management", icon: "🛡️", label: "Admin Management", color: "#6366f1", bg: "#eef2ff" },
    { role: ["ADMIN", "MANAGER"], to: "/dashboard/dean-management", icon: "🎓", label: "Dean Management", color: "#0891b2", bg: "#ecfeff" },
    { role: ["ADMIN", "MANAGER"], to: "/dashboard/vice-dean-management", icon: "👔", label: "Vice Dean Management", color: "#7c3aed", bg: "#f5f3ff" },
    { role: ["ADMIN", "MANAGER"], to: "/dashboard/teacher-management", icon: "👩‍🏫", label: "Teacher Management", color: "#16a34a", bg: "#f0fdf4" },
    { role: ["ADMIN", "MANAGER", "ASSISTANT_MANAGER"], to: "/dashboard/student-management", icon: "🎒", label: "Student Management", color: "#d97706", bg: "#fffbeb" },
    { role: ["ADMIN"], to: "/dashboard/lessons-management", icon: "📚", label: "Lesson Management", color: "#dc2626", bg: "#fef2f2" },
    { role: ["ADMIN"], to: "/dashboard/contact-messages", icon: "✉️", label: "Contact Messages", color: "#0891b2", bg: "#ecfeff" },
    { role: ["TEACHER"], to: "/dashboard/student-info-management", icon: "📋", label: "Student Info", color: "#16a34a", bg: "#f0fdf4" },
    { role: ["TEACHER"], to: "/dashboard/meet-management", icon: "🤝", label: "Meetings", color: "#7c3aed", bg: "#f5f3ff" },
    { role: ["TEACHER", "STUDENT"], to: "/dashboard/grades-meets", icon: "📊", label: "Grades & Meets", color: "#d97706", bg: "#fffbeb" },
    { role: ["STUDENT"], to: "/dashboard/choose-lesson", icon: "🗓️", label: "Choose Lessons", color: "#1e40af", bg: "#eff6ff" },
];

const DashboardPage = () => {
    const { user } = useSelector((s) => s.auth);
    const role = user?.role || "";

    const cards = allCards.filter((c) => c.role.includes(role));

    return (
        <div>
            <div className="page-header">
                <div>
                    <h4>Dashboard</h4>
                    <p className="text-muted mb-0" style={{ fontSize: ".85rem" }}>
                        Welcome back, <strong>{user?.name || user?.username}</strong> —{" "}
                        <span className="badge" style={{ background: "#eff6ff", color: "#1e40af" }}>
                            {role.replace("_", " ")}
                        </span>
                    </p>
                </div>
            </div>

            {cards.length === 0 ? (
                <div className="alert alert-info">No management sections available for your role.</div>
            ) : (
                <div className="row g-3">
                    {cards.map((c) => (
                        <div className="col-6 col-md-4 col-lg-3" key={c.to}>
                            <Link to={c.to} className="nav-card">
                                <div className="nav-card-icon" style={{ background: c.bg, color: c.color }}>
                                    {c.icon}
                                </div>
                                <span className="nav-card-label">{c.label}</span>
                            </Link>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default DashboardPage;
