import React from "react";
import { FiTarget, FiEye, FiHeart } from "react-icons/fi";

const team = [
    { name: "Dr. James Wilson", role: "Principal", emoji: "👨‍💼" },
    { name: "Prof. Sarah Chen", role: "Vice Principal", emoji: "👩‍💼" },
    { name: "Dr. Michael Brown", role: "Head of Academics", emoji: "👨‍🏫" },
    { name: "Ms. Emily Davis", role: "Student Affairs", emoji: "👩‍🏫" },
];

const AboutPage = () => (
    <div>
        <div style={{ background: "linear-gradient(135deg,#1e3a8a,#1e40af)", color: "#fff", padding: "4rem 0" }}>
            <div className="container text-center">
                <h2 className="fw-bold mb-3">About CampusMS</h2>
                <p style={{ opacity: .85, maxWidth: 560, margin: "0 auto" }}>Dedicated to empowering educational institutions with modern tools and technology.</p>
            </div>
        </div>
        <div className="container py-5">
            <div className="row g-4 mb-5">
                {[
                    { icon: <FiTarget size={24} />, title: "Our Mission", bg: "#eff6ff", color: "#1e40af", desc: "To provide schools with a seamless, all-in-one management platform that reduces administrative burden and enhances educational outcomes." },
                    { icon: <FiEye size={24} />, title: "Our Vision", bg: "#f0fdf4", color: "#16a34a", desc: "A world where every school operates efficiently, enabling educators to focus on what matters most — student success." },
                    { icon: <FiHeart size={24} />, title: "Our Values", bg: "#fef3c7", color: "#d97706", desc: "Excellence, transparency, accessibility, and continuous improvement in everything we build and deliver." },
                ].map((c, i) => (
                    <div className="col-md-4" key={i}>
                        <div className="card h-100 p-4 border-0" style={{ background: c.bg }}>
                            <div className="mb-3" style={{ color: c.color }}>{c.icon}</div>
                            <h5 className="fw-semibold">{c.title}</h5>
                            <p className="text-muted mb-0" style={{ fontSize: ".88rem" }}>{c.desc}</p>
                        </div>
                    </div>
                ))}
            </div>

            <div className="row align-items-center g-5 mb-5">
                <div className="col-lg-6">
                    <h3 className="fw-bold mb-3">Why Choose CampusMS?</h3>
                    <p className="text-muted">Founded with a commitment to educational excellence, CampusMS serves hundreds of institutions worldwide. Our platform integrates seamlessly with existing workflows and grows with your institution's needs.</p>
                    <ul className="list-unstyled mt-3">
                        {["15+ years of educational technology", "Trusted by 500+ institutions", "24/7 customer support", "Regular feature updates"].map((t) => (
                            <li key={t} className="mb-2 d-flex align-items-center gap-2" style={{ color: "#475569" }}>
                                <span style={{ color: "#16a34a", fontWeight: 700 }}>✓</span> {t}
                            </li>
                        ))}
                    </ul>
                </div>
                <div className="col-lg-6">
                    <div className="row g-3">
                        {[["1,200+", "Students Enrolled"], ["80+", "Courses Available"], ["120+", "Expert Teachers"], ["98%", "Satisfaction Rate"]].map(([v, l]) => (
                            <div className="col-6" key={l}>
                                <div className="card p-3 text-center border-0" style={{ background: "#f8fafc" }}>
                                    <div style={{ fontSize: "1.75rem", fontWeight: 700, color: "#1e40af" }}>{v}</div>
                                    <div style={{ fontSize: ".8rem", color: "#64748b" }}>{l}</div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            <h4 className="fw-bold mb-4">Leadership Team</h4>
            <div className="row g-3">
                {team.map((m) => (
                    <div className="col-md-6 col-lg-3" key={m.name}>
                        <div className="card p-4 text-center border-0 h-100" style={{ background: "#f8fafc" }}>
                            <div style={{ fontSize: "3rem", marginBottom: ".75rem" }}>{m.emoji}</div>
                            <h6 className="fw-semibold mb-1">{m.name}</h6>
                            <span className="badge" style={{ background: "#eff6ff", color: "#1e40af" }}>{m.role}</span>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    </div>
);

export default AboutPage;
