import React from "react";
import { Link } from "react-router-dom";
import { FiUsers, FiBook, FiCalendar, FiAward, FiArrowRight, FiCheckCircle } from "react-icons/fi";

const features = [
    { icon: "🎓", title: "Student Management", desc: "Track enrollment, grades, attendance, and academic progress in one place." },
    { icon: "👩‍🏫", title: "Teacher Portal", desc: "Manage lesson programs, student meetings, and grade submissions efficiently." },
    { icon: "📚", title: "Course Management", desc: "Create and assign courses with credit scores, schedules, and terms." },
    { icon: "📊", title: "Academic Reports", desc: "Generate detailed reports on student performance with automated grading." },
    { icon: "🗓️", title: "Event Scheduling", desc: "Organize meetings, exams, and school events with calendar integration." },
    { icon: "🔐", title: "Role-Based Access", desc: "Secure access levels for admins, deans, teachers, and students." },
];

const stats = [
    { icon: <FiUsers />, value: "1,200+", label: "Students" },
    { icon: <FiBook />, value: "80+", label: "Courses" },
    { icon: <FiUsers />, value: "120+", label: "Teachers" },
    { icon: <FiAward />, value: "15+", label: "Years of Excellence" },
];

const HomePage = () => (
    <div>
        <section className="hero-section">
            <div className="container">
                <div className="row align-items-center g-5">
                    <div className="col-lg-6">
                        <span className="badge mb-3 px-3 py-2" style={{ background: "rgba(255,255,255,.15)", color: "#fff", borderRadius: "2rem" }}>
                            Welcome to CampusMS
                        </span>
                        <h1 className="mb-4">Modern School Management Made Simple</h1>
                        <p className="mb-4">
                            A unified platform for administrators, teachers, and students — streamlining
                            every aspect of academic life from enrollment to graduation.
                        </p>
                        <div className="d-flex gap-3 flex-wrap">
                            <Link to="/login" className="btn btn-light btn-lg px-4 fw-semibold">
                                Get Started <FiArrowRight className="ms-1" />
                            </Link>
                            <Link to="/about" className="btn btn-outline-light btn-lg px-4">
                                Learn More
                            </Link>
                        </div>
                    </div>
                    <div className="col-lg-6 d-none d-lg-block text-center">
                        <div style={{ background: "rgba(255,255,255,.1)", borderRadius: "1.5rem", padding: "2.5rem", backdropFilter: "blur(10px)", border: "1px solid rgba(255,255,255,.2)" }}>
                            <div className="row g-3">
                                {stats.map((s, i) => (
                                    <div className="col-6" key={i}>
                                        <div style={{ background: "rgba(255,255,255,.1)", borderRadius: ".75rem", padding: "1.25rem" }}>
                                            <div style={{ fontSize: "1.5rem", opacity: .9, marginBottom: ".35rem" }}>{s.icon}</div>
                                            <div style={{ fontSize: "1.5rem", fontWeight: 700 }}>{s.value}</div>
                                            <div style={{ fontSize: ".78rem", opacity: .8 }}>{s.label}</div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section className="py-5">
            <div className="container">
                <div className="text-center mb-5">
                    <span style={{ color: "#1e40af", fontWeight: 600, fontSize: ".8rem", textTransform: "uppercase", letterSpacing: ".08em" }}>What We Offer</span>
                    <h2 className="mt-2 fw-bold" style={{ color: "#0f172a" }}>Everything You Need to Run a School</h2>
                    <p className="text-muted mx-auto" style={{ maxWidth: 520 }}>From student enrollment to final grades — all academic operations in one platform.</p>
                </div>
                <div className="row g-4">
                    {features.map((f, i) => (
                        <div className="col-md-6 col-lg-4" key={i}>
                            <div className="feature-card">
                                <div className="feature-icon">{f.icon}</div>
                                <h5>{f.title}</h5>
                                <p>{f.desc}</p>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </section>

        <section className="py-5" style={{ background: "#eff6ff" }}>
            <div className="container">
                <div className="row align-items-center g-4">
                    <div className="col-lg-7">
                        <h3 className="fw-bold" style={{ color: "#0f172a" }}>Ready to Transform Your School?</h3>
                        <p className="text-muted mb-4">Join hundreds of institutions already using CampusMS to streamline their operations.</p>
                        <ul className="list-unstyled d-flex flex-column gap-2">
                            {["Easy setup in minutes", "Role-based access control", "Real-time grade tracking"].map((t) => (
                                <li key={t} className="d-flex align-items-center gap-2" style={{ color: "#475569" }}>
                                    <FiCheckCircle color="#16a34a" size={16} /> {t}
                                </li>
                            ))}
                        </ul>
                    </div>
                    <div className="col-lg-5 text-center text-lg-end">
                        <Link to="/login" className="btn btn-primary btn-lg px-5">
                            Access Portal <FiArrowRight className="ms-2" />
                        </Link>
                    </div>
                </div>
            </div>
        </section>
    </div>
);

export default HomePage;
