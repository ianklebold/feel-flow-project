import React from "react";
import { Link } from "react-router-dom";
import modulosData from '../assets/data/modulos.json';
import FeelFlow from '../assets/img/FeelFlow.png';
import { FaHome, FaChartPie, FaUserCircle, FaUsers, FaPuzzlePiece, FaIdCard, FaCog } from "react-icons/fa";

function Sidebar() {
    const modulos = Object.values(modulosData);

    const iconMapping = {
        "fa-home": <FaHome className="text-gray-500" />, // Home
        "fa-pie-chart": <FaChartPie className="text-gray-500" />, // Dashboard
        "fa-user-circle": <FaUserCircle className="text-gray-500" />, // Lideres, Usuarios
        "fa-users": <FaUsers className="text-gray-500" />, // Equipos
        "fa-puzzle-piece": <FaPuzzlePiece className="text-gray-500" />, // Modulos
        "fa-id-card": <FaIdCard className="text-gray-500" />, // Perfil
        "fa-cog": <FaCog className="text-gray-500" />, // Configuración
    };

    return (
        <div className="relative min-h-screen bg-bgPrimary flex flex-col w-20 hover:w-64 transition-all duration-300">
            {/* Modulos */}
            <ul className="flex flex-col space-y-3 text-xl px-2">
                {modulos.map((modulo, index) => (
                    <li className="nav-item" key={index}>
                        <Link
                            to={modulo.link}
                            className="nav-link flex items-center p-2 rounded hover:bg-bgBlueSecondary hover:text-black transition-colors duration-200"
                        >
                            {/* Ícono del módulo */}
                            <div className="icon icon-shape icon-sm shadow border-radius-md flex items-center justify-center text-gray-500 mr-4">
                                {iconMapping[modulo.logo] || (
                                    <span className="text-gray-500">N/A</span>
                                )}
                            </div>
                            {/* Texto del módulo */}
                            <span className="nav-link-text whitespace-nowrap overflow-hidden transition-all duration-300 group-hover:opacity-100 group-hover:w-auto group-hover:visible opacity-0">
                                {modulo.nombre}
                            </span>
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Sidebar;
