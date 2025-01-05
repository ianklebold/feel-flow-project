import React from "react";
import { Helmet } from "react-helmet";
import ProfileBanner from "../components/ProfileBanner";
import ProfileInfo from "../components/ProfileInfo";

function Profile() {
  return (
    <div className="flex">

      {/* Main Content */}
      <div className="flex-1 bg-gray-100 min-h-screen p-6">
        {/* Helmet para el título de la página */}
        <Helmet>
          <title>Perfil</title>
        </Helmet>

        {/* Contenido del Perfil */}
        <div className="mt-6 space-y-6">
          {/* Banner del Perfil */}
          <ProfileBanner />

          {/* Información del Perfil */}
          <ProfileInfo />
        </div>
      </div>
    </div>
  );
}

export default Profile;
