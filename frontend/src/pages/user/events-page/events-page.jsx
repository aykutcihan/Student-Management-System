import React from "react";
import { FiCalendar, FiClock, FiMapPin } from "react-icons/fi";

const events = [
    { title: "Fall Semester Orientation", date: "September 2, 2025", time: "9:00 AM", location: "Main Auditorium", type: "Academic", emoji: "🎓" },
    { title: "Mid-Term Examination Period", date: "October 20–25, 2025", time: "8:00 AM", location: "Exam Halls", type: "Exam", emoji: "📝" },
    { title: "Science Fair 2025", date: "November 5, 2025", time: "10:00 AM", location: "Sports Complex", type: "Competition", emoji: "🔬" },
    { title: "Annual Sports Day", date: "November 15, 2025", time: "8:00 AM", location: "Sports Ground", type: "Sports", emoji: "🏆" },
    { title: "Cultural Festival", date: "December 1, 2025", time: "11:00 AM", location: "Main Campus", type: "Cultural", emoji: "🎭" },
    { title: "Final Examinations", date: "January 10–20, 2026", time: "8:00 AM", location: "Exam Halls", type: "Exam", emoji: "📚" },
];

const typeColors = {
    Academic: { bg: "#eff6ff", color: "#1e40af" },
    Exam: { bg: "#fef3c7", color: "#d97706" },
    Competition: { bg: "#f0fdf4", color: "#16a34a" },
    Sports: { bg: "#fff7ed", color: "#ea580c" },
    Cultural: { bg: "#fdf4ff", color: "#9333ea" },
};

const EventsPage = () => (
    <div>
        <div style={{ background: "linear-gradient(135deg,#1e3a8a,#1e40af)", color: "#fff", padding: "4rem 0" }}>
            <div className="container text-center">
                <h2 className="fw-bold mb-2">Upcoming Events</h2>
                <p style={{ opacity: .85 }}>Stay up to date with all academic and extracurricular activities.</p>
            </div>
        </div>
        <div className="container py-5">
            <div className="row g-4">
                {events.map((e) => {
                    const tc = typeColors[e.type] || { bg: "#f8fafc", color: "#64748b" };
                    return (
                        <div className="col-md-6 col-lg-4" key={e.title}>
                            <div className="card h-100 border-0 shadow-sm p-4" style={{ borderRadius: ".75rem" }}>
                                <div className="d-flex justify-content-between align-items-start mb-3">
                                    <span style={{ fontSize: "2rem" }}>{e.emoji}</span>
                                    <span className="badge" style={{ background: tc.bg, color: tc.color }}>{e.type}</span>
                                </div>
                                <h6 className="fw-bold mb-3">{e.title}</h6>
                                <ul className="list-unstyled mb-0" style={{ fontSize: ".83rem", color: "#64748b" }}>
                                    <li className="d-flex align-items-center gap-2 mb-2"><FiCalendar size={13} /> {e.date}</li>
                                    <li className="d-flex align-items-center gap-2 mb-2"><FiClock size={13} /> {e.time}</li>
                                    <li className="d-flex align-items-center gap-2"><FiMapPin size={13} /> {e.location}</li>
                                </ul>
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    </div>
);

export default EventsPage;
