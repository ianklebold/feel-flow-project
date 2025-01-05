import React from "react";
import { Helmet } from "react-helmet";
import ProfileBanner from "../components/ProfileBanner";
// import ProfileMiddle from "../components/ProfileMiddle";
// import ProfileInfo from "../components/ProfileInfo";
// import ProfileBanner from "../components/ProfileBanner";
import ProfileCard from "../components/ProfileCard";
import ProfileDetails from "../components/ProfileDetails";

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
          <div className="space-y-8">
          {/* Banner de Perfil */}
          <ProfileBanner />

          {/* Tarjeta del Perfil */}
          <ProfileCard />

          {/* Detalles del Perfil */}
          <ProfileDetails />
        </div>
      </div>
    </div>
  );
}

export default Profile;
