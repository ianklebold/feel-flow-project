import React, { useState } from "react";
import { FaUserEdit } from "react-icons/fa";
import Profile from "../assets/img/profile.jpg";
import EditProfile from "../pages/EditProfile.jsx"; 
import Popup from "../components/PopupEditProfile"; 

function ProfileCard({ user, token }) {
  const [isPopupOpen, setIsPopupOpen] = useState(false);

  const openPopup = () => setIsPopupOpen(true);
  const closePopup = () => setIsPopupOpen(false);

  return (
    <div className="relative mx-auto w-full max-w-4xl bg-gradient-to-r from-indigo-200 via-indigo-100 to-white shadow-xl rounded-3xl p-6 flex items-center space-x-6">
      {/* Imagen de Perfil */}
      <div className="w-24 h-24 rounded-full overflow-hidden border-4 border-gray-200 shadow-lg transform hover:scale-110 transition duration-300 ease-in-out">
        <img
          src={Profile}
          alt="Avatar"
          className="w-full h-full object-cover"
        />
      </div>

      {/* Información del Usuario */}
      <div className="flex-1">
        <h1 className="text-2xl font-semibold text-gray-800 mb-1">{user.name}</h1>
        <p className="text-sm text-gray-500">{user.role}</p>
      </div>

      {/* Botón de Editar */}
      <button
        onClick={openPopup}
        className="flex items-center space-x-2 text-blue-600 hover:text-blue-800 transition duration-200"
      >
        <FaUserEdit />
        <span className="font-medium">Editar Perfil</span>
      </button>

      {/* Popup */}
      <Popup
        isOpen={isPopupOpen}
        title="Editar Perfil"
        buttons={[
          {
            label: "Cerrar",
            onClick: closePopup,
            color: "red",
          },
        ]}
      >
        <EditProfile userId={user.id} token={token} />
      </Popup>
    </div>
  );
}

export default ProfileCard;
