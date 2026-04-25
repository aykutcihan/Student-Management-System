import React from "react";
import { Link } from "react-router-dom";
import { FiMail, FiPhone, FiMapPin, FiFacebook, FiTwitter, FiLinkedin } from "react-icons/fi";

const Footer = () => (
    <footer className="site-footer">
        <div className="container">
            <div className="row g-4">
                <div className="col-lg-4">
                    <h6 className="mb-3">CampusMS</h6>
                    <p style={{ fontSize: ".85rem" }}>
                        A comprehensive school management system that connects students,
                        teachers, and administrators in one unified platform.
                    </p>
                    <div className="d-flex gap-2 mt-3">
                        {[FiFacebook, FiTwitter, FiLinkedin].map((Icon, i) => (
                            <a
                                key={i}
                                href="#!"
                                className="d-flex align-items-center justify-content-center"
                                style={{
                                    width: 32, height: 32, borderRadius: 8,
                                    background: "rgba(255,255,255,.08)",
                                    color: "#94a3b8",
                                }}
                            >
                                <Icon size={15} />
                            </a>
                        ))}
                    </div>
                </div>

                <div className="col-lg-2 col-6">
                    <h6 className="mb-3" style={{ fontSize: ".82rem" }}>Quick Links</h6>
                    <ul className="list-unstyled" style={{ fontSize: ".83rem" }}>
                        {[["Home", "/"], ["About", "/about"], ["Courses", "/courses"], ["Events", "/events"], ["Contact", "/contact"]].map(([label, to]) => (
                            <li key={to} className="mb-1">
                                <Link to={to}>{label}</Link>
                            </li>
                        ))}
                    </ul>
                </div>

                <div className="col-lg-2 col-6">
                    <h6 className="mb-3" style={{ fontSize: ".82rem" }}>Portal</h6>
                    <ul className="list-unstyled" style={{ fontSize: ".83rem" }}>
                        {[["Login", "/login"], ["Dashboard", "/dashboard"], ["Unauthorized", "/unauthorized"]].map(([label, to]) => (
                            <li key={to} className="mb-1">
                                <Link to={to}>{label}</Link>
                            </li>
                        ))}
                    </ul>
                </div>

                <div className="col-lg-4">
                    <h6 className="mb-3" style={{ fontSize: ".82rem" }}>Contact</h6>
                    <ul className="list-unstyled" style={{ fontSize: ".83rem" }}>
                        <li className="mb-2 d-flex align-items-center gap-2"><FiMapPin size={13} /> 123 University Ave, City</li>
                        <li className="mb-2 d-flex align-items-center gap-2"><FiPhone size={13} /> +1 (555) 000-0000</li>
                        <li className="mb-2 d-flex align-items-center gap-2"><FiMail size={13} /> info@campus.edu</li>
                    </ul>
                </div>
            </div>

            <div className="footer-bottom d-flex justify-content-between align-items-center">
                <span>&copy; {new Date().getFullYear()} CampusMS. All rights reserved.</span>
                <span>School Management System</span>
            </div>
        </div>
    </footer>
);

export default Footer;
