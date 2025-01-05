import { useState, useEffect } from "react";
import { Helmet } from "react-helmet";
import ProfileBanner from "../components/ProfileBanner";
import ProfileCard from "../components/ProfileCard";
import ProfileDetails from "../components/ProfileDetails";
import { getProfileData } from "../services/Auth/Profile";  // Un servicio que implementas para obtener los datos del perfil

function Profile() {
    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
      const fetchProfileData = async () => {
          try {
              const data = await getProfileData();  // Realizar la solicitud con el token
              console.log("Datos del perfil recibidos:", data);  // Verifica los datos del perfil
              setProfile(data);
              setLoading(false);
          } catch (error) {
              console.error("Error al obtener los datos del perfil:", error);
              setError("No se pudieron cargar los datos del perfil.");
              setLoading(false);
          }
      };
  
      fetchProfileData();
  }, []);  

    if (loading) {
        return <div>Cargando...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className="min-h-screen bg-gray-50">
            <Helmet>
                <title>Perfil | Feel Flow</title>
            </Helmet>

            <div className="space-y-8">
                <ProfileBanner />

                {/* Mostrar datos del perfil si están disponibles */}
                {profile && (
                    <>
                        <ProfileCard user={profile} />
                        <ProfileDetails user={profile} />
                    </>
                )}
            </div>
        </div>
    );
}

export default Profile;
