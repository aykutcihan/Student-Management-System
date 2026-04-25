import React from "react";

const Footer = () => {
    return (
        <footer className="bg-dark text-white text-center py-3 mt-auto small">
            <p className="mb-0">
                &copy; {new Date().getFullYear()} School Management System. Tüm hakları saklıdır.
            </p>
        </footer>
    );
};

export default Footer;
