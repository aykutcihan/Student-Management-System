import React from "react";
import RootLayout from "./root-layout";
import { Outlet, NavLink, useLocation } from "react-router-dom";
import Topbar from "../components/common/topbar/topbar";
import Menubar from "../components/common/menubar/menubar";
import Footer from "../components/common/footer/footer";
import { useSelector } from "react-redux";
import { FiGrid, FiUsers, FiUser, FiBook, FiMail, FiCalendar, FiClipboard, FiAward, FiCheckSquare } from "react-icons/fi";

const sidebarLinks = {
    ADMIN: [
        { to: "/dashboard", label: "Overview", icon: <FiGrid size={16}/>, end: true },
        { to: "/dashboard/admin-management", label: "Admins", icon: <FiUsers size={16}/> },
        { to: "/dashboard/dean-management", label: "Deans", icon: <FiUser size={16}/> },
        { to: "/dashboard/vice-dean-management", label: "Vice Deans", icon: <FiUser size={16}/> },
        { to: "/dashboard/teacher-management", label: "Teachers", icon: <FiUsers size={16}/> },
        { to: "/dashboard/student-management", label: "Students", icon: <FiUsers size={16}/> },
        { to: "/dashboard/lessons-management", label: "Lessons", icon: <FiBook size={16}/> },
        { to: "/dashboard/contact-messages", label: "Messages", icon: <FiMail size={16}/> },
    ],
    MANAGER: [
        { to: "/dashboard", label: "Overview", icon: <FiGrid size={16}/>, end: true },
        { to: "/dashboard/dean-management", label: "Deans", icon: <FiUser size={16}/> },
        { to: "/dashboard/vice-dean-management", label: "Vice Deans", icon: <FiUser size={16}/> },
        { to: "/dashboard/teacher-management", label: "Teachers", icon: <FiUsers size={16}/> },
        { to: "/dashboard/student-management", label: "Students", icon: <FiUsers size={16}/> },
    ],
    ASSISTANT_MANAGER: [
        { to: "/dashboard", label: "Overview", icon: <FiGrid size={16}/>, end: true },
        { to: "/dashboard/student-management", label: "Students", icon: <FiUsers size={16}/> },
    ],
    TEACHER: [
        { to: "/dashboard", label: "Overview", icon: <FiGrid size={16}/>, end: true },
        { to: "/dashboard/student-info-management", label: "Student Info", icon: <FiClipboard size={16}/> },
        { to: "/dashboard/meet-management", label: "Meetings", icon: <FiCalendar size={16}/> },
        { to: "/dashboard/grades-meets", label: "Grades & Meets", icon: <FiAward size={16}/> },
    ],
    STUDENT: [
        { to: "/dashboard", label: "Overview", icon: <FiGrid size={16}/>, end: true },
        { to: "/dashboard/choose-lesson", label: "Choose Lessons", icon: <FiCheckSquare size={16}/> },
        { to: "/dashboard/grades-meets", label: "Grades & Meets", icon: <FiAward size={16}/> },
    ],
};

const UserLayout = () => {
    const { isLoggedIn, user } = useSelector((s) => s.auth);
    const location = useLocation();
    const isDashboard = location.pathname.startsWith("/dashboard");
    const links = (user?.role && sidebarLinks[user.role]) || [];

    return (
        <RootLayout>
            <div className="d-flex flex-column min-vh-100">
                <Topbar />
                <Menubar />
                <div className="flex-grow-1">
                    {isDashboard && isLoggedIn ? (
                        <div className="dashboard-layout">
                            <aside className="sidebar d-none d-md-block">
                                <div className="sidebar-header">Navigation</div>
                                {links.map((l) => (
                                    <NavLink key={l.to} to={l.to} end={l.end} className={({ isActive }) => "sidebar-link" + (isActive ? " active" : "")}>
                                        {l.icon} {l.label}
                                    </NavLink>
                                ))}
                            </aside>
                            <main className="dashboard-content">
                                <Outlet />
                            </main>
                        </div>
                    ) : (
                        <main>
                            <Outlet />
                        </main>
                    )}
                </div>
                <Footer />
            </div>
        </RootLayout>
    );
};

export default UserLayout;
