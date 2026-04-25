import React from "react";
import { FiClock, FiUsers, FiStar } from "react-icons/fi";

const courses = [
    { title: "Introduction to Mathematics", dept: "Mathematics", credits: 3, students: 45, rating: 4.8, compulsory: true, emoji: "📐" },
    { title: "English Language & Literature", dept: "Languages", credits: 4, students: 60, rating: 4.7, compulsory: true, emoji: "📝" },
    { title: "Physics Fundamentals", dept: "Science", credits: 4, students: 38, rating: 4.6, compulsory: false, emoji: "⚛️" },
    { title: "World History", dept: "Humanities", credits: 3, students: 52, rating: 4.5, compulsory: false, emoji: "🌍" },
    { title: "Computer Science Basics", dept: "Technology", credits: 3, students: 70, rating: 4.9, compulsory: false, emoji: "💻" },
    { title: "Biology & Life Sciences", dept: "Science", credits: 4, students: 42, rating: 4.6, compulsory: false, emoji: "🧬" },
    { title: "Chemistry", dept: "Science", credits: 4, students: 35, rating: 4.4, compulsory: true, emoji: "🧪" },
    { title: "Physical Education", dept: "Sports", credits: 2, students: 80, rating: 4.8, compulsory: true, emoji: "🏃" },
    { title: "Art & Design", dept: "Arts", credits: 2, students: 30, rating: 4.7, compulsory: false, emoji: "🎨" },
];

const CoursesPage = () => (
    <div>
        <div style={{ background: "linear-gradient(135deg,#1e3a8a,#1e40af)", color: "#fff", padding: "4rem 0" }}>
            <div className="container text-center">
                <h2 className="fw-bold mb-2">Available Courses</h2>
                <p style={{ opacity: .85 }}>Explore our comprehensive curriculum designed for academic excellence.</p>
            </div>
        </div>
        <div className="container py-5">
            <div className="row g-4">
                {courses.map((c) => (
                    <div className="col-md-6 col-lg-4" key={c.title}>
                        <div className="card h-100 border-0 shadow-sm overflow-hidden" style={{ borderRadius: ".75rem" }}>
                            <div className="p-4">
                                <div className="d-flex justify-content-between align-items-start mb-3">
                                    <span style={{ fontSize: "2.5rem" }}>{c.emoji}</span>
                                    {c.compulsory && <span className="badge" style={{ background: "#fef3c7", color: "#d97706" }}>Required</span>}
                                </div>
                                <h6 className="fw-bold mb-1">{c.title}</h6>
                                <span className="badge mb-3" style={{ background: "#eff6ff", color: "#1e40af" }}>{c.dept}</span>
                                <div className="d-flex justify-content-between" style={{ fontSize: ".8rem", color: "#64748b" }}>
                                    <span className="d-flex align-items-center gap-1"><FiClock size={12} /> 16 weeks</span>
                                    <span className="d-flex align-items-center gap-1"><FiUsers size={12} /> {c.students}</span>
                                    <span className="d-flex align-items-center gap-1"><FiStar size={12} /> {c.rating}</span>
                                </div>
                            </div>
                            <div className="px-4 pb-4 d-flex justify-content-between align-items-center border-top pt-3" style={{ borderColor: "#f1f5f9" }}>
                                <span style={{ fontSize: ".82rem", color: "#64748b" }}>{c.credits} credit hours</span>
                                <span className="badge bg-primary">{c.credits} Credits</span>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    </div>
);

export default CoursesPage;
