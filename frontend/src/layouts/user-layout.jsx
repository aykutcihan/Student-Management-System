import React from "react";
import RootLayout from "./root-layout";
import { Outlet } from "react-router-dom";
import Topbar from "../components/common/topbar/topbar";
import Menubar from "../components/common/menubar/menubar";
import Footer from "../components/common/footer/footer";

const UserLayout = () => {
    return (
        <RootLayout>
            <div className="d-flex flex-column min-vh-100">
                <Topbar />
                <Menubar />
                <main className="flex-grow-1">
                    <Outlet />
                </main>
                <Footer />
            </div>
        </RootLayout>
    );
};

export default UserLayout;
