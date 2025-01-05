import React from "react";
import { FaUser } from "react-icons/fa";

function ProfileDetails({ user }) {
    return (
        <div className="mx-auto w-11/12 max-w-4xl bg-white shadow-md rounded-lg p-6">
            <h2 className="text-lg font-semibold text-gray-800 mb-4 flex items-center space-x-2">
                <FaUser />
                <span>Información del Perfil</span>
            </h2>

            <div className="space-y-2">
                <p>
                    <span className="font-semibold text-gray-700">Nombre Completo:</span> {user.name}
                </p>
                <p>
                    <span className="font-semibold text-gray-700">Email:</span> {user.email}
                </p>
                <p>
                    <span className="font-semibold text-gray-700">Empresa:</span> {user.company}
                </p>
                <p>
                    <span className="font-semibold text-gray-700">Equipo:</span> {user.team}
                </p>
                <p>
                    <span className="font-semibold text-gray-700">Rol:</span> {user.role}
                </p>
            </div>
        </div>
    );
}

export default ProfileDetails;
