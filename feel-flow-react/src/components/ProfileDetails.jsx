import React from "react";
import { FaUser, FaEnvelope, FaBuilding, FaIdCard } from "react-icons/fa";

function ProfileDetails({ user }) {
  return (
    <div className="max-w-4xl mx-auto bg-white rounded-3xl p-8 shadow-xl space-y-8">
      <h2 className="text-4xl font-bold text-gray-800 flex items-center space-x-3 mb-6">
        <FaUser className="text-indigo-600 text-3xl" />
        <span>Detalles del Perfil</span>
      </h2>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {/* Nombre Completo */}
        <div className="bg-gradient-to-r from-indigo-100 to-indigo-50 p-6 rounded-2xl shadow-lg hover:shadow-2xl transition-all duration-300 transform hover:scale-105">
          <div className="flex items-center space-x-4">
            <FaUser className="text-indigo-600 text-3xl" />
            <div>
              <h3 className="font-semibold text-gray-700">Nombre Completo</h3>
              <p className="text-gray-600">{`${user.name} ${user.surname}`}</p>
            </div>
          </div>
        </div>

        {/* Correo */}
        <div className="bg-gradient-to-r from-indigo-100 to-indigo-50 p-6 rounded-2xl shadow-lg hover:shadow-2xl transition-all duration-300 transform hover:scale-105">
          <div className="flex items-center space-x-4">
            <FaEnvelope className="text-indigo-600 text-3xl" />
            <div>
              <h3 className="font-semibold text-gray-700">Correo</h3>
              <p className="text-gray-600">{user.username}</p>
            </div>
          </div>
        </div>

        {/* Empresa */}
        <div className="bg-gradient-to-r from-indigo-100 to-indigo-50 p-6 rounded-2xl shadow-lg hover:shadow-2xl transition-all duration-300 transform hover:scale-105">
          <div className="flex items-center space-x-4">
            <FaBuilding className="text-indigo-600 text-3xl" />
            <div>
              <h3 className="font-semibold text-gray-700">Empresa</h3>
              <p className="text-gray-600">{user.enterpriseName || "No disponible"}</p>
            </div>
          </div>
        </div>

        {/* ID de Empresa */}
        <div className="bg-gradient-to-r from-indigo-100 to-indigo-50 p-6 rounded-2xl shadow-lg hover:shadow-2xl transition-all duration-300 transform hover:scale-105">
          <div className="flex items-center space-x-4">
            <FaIdCard className="text-indigo-600 text-3xl" />
            <div>
              <h3 className="font-semibold text-gray-700">ID de Empresa</h3>
              <p className="text-gray-600">{user.enterpriseID || "No disponible"}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProfileDetails;
