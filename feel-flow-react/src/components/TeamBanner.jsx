import React from "react";

const TeamBanner = ({ bannerUrl = "https://via.placeholder.com/1200x300", name }) => {
  return (
    <div className="relative w-full h-48 bg-gray-700 rounded-lg overflow-hidden shadow-lg">
      <img
        src={bannerUrl}
        alt="Team Banner"
        className="absolute inset-0 object-cover w-full h-full"
      />
      <div className="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-between px-6">
        <h1 className="text-3xl font-bold text-white">{name || "Nombre del Equipo"}</h1>
        <div className="space-y-4 flex flex-col items-end">
          <button className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 w-32">
            Editar
          </button>
          <button className="bg-pink-500 text-white px-4 py-2 rounded hover:bg-pink-600 w-32">
            Eliminar
          </button>
        </div>
      </div>
    </div>
  );
};

export default TeamBanner;
