import React from "react";

import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";

import { validateToken } from "../services/session";

function FeelFlow({ children , onLogout } ) {
    const checkToken = async () => {
        try {
            const isValid = await validateToken();
            return isValid;
        } catch (error) {
            console.error("Error al validar el token:", error);
            return false;
        }
    };
    if (!checkToken()) {
        onLogout();
    } 

    return (
        <div className="flex flex-col min-h-screen">
            {/* Navbar */}
            <header className="w-full h-16 bg-light border-b border-textPrimary fixed top-0 left-0 z-20 font-display text-textPrimary">
                <Navbar onLogout={onLogout} />
            </header>

            <div className="flex flex-1 pt-16">
                {/* Sidebar */}
                <aside className="bg-light border-r border-textPrimary w-14 hover:w-44 transition-all duration-300 overflow-hidden h-screen fixed top-16 left-0 z-10 font-display">
                    <Sidebar />
                </aside>

                {/* Main Content */}
                <main className="ml-20 flex-1 p-4">
                    {children}
                </main>
            </div>
        </div>
    );
}

export default FeelFlow;
