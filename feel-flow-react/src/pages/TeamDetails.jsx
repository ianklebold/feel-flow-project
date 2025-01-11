import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { GetEquipobyID } from "../services/GetEquipoId";
import TeamBanner from "../components/TeamBanner";
import TeamDetailsCard from "../components/TeamDetailsCard";
import TeamMembersList from "../components/TeamMembersList";
import { getAuthData } from "../services/session";

const TeamDetails = () => {
  const { teamId } = useParams(); // Captura el UUID desde la URL
  const navigate = useNavigate();
  const [teamDetails, setTeamDetails] = useState(null);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  const uuid = sessionStorage.getItem("teamID") || teamId;

  useEffect(() => {
    if (!uuid) {
      console.error("UUID del equipo no proporcionado. Redirigiendo a equipos.");
      setError("No se pudo identificar el equipo.");
      navigate("/teams");
      return;
    }

    // Guardar el UUID en sessionStorage para consistencia
    sessionStorage.setItem("teamID", uuid);

    const fetchTeamDetails = async () => {
      const { token } = getAuthData();

      if (!token) {
        console.error("Token no encontrado. Redirigiendo al login.");
        setError("No se encontró el token. Redirigiendo al login.");
        setTimeout(() => navigate("/login"), 2000);
        return;
      }

      try {
        const data = await GetEquipobyID(token, uuid);
        console.log("Detalles del equipo recibidos:", data);
        setTeamDetails(data);
      } catch (err) {
        console.error("Error al obtener los detalles del equipo:", err);
        setError("No se pudieron cargar los detalles del equipo.");
      } finally {
        setIsLoading(false);
      }
    };

    fetchTeamDetails();
  }, [uuid, navigate]);

  if (isLoading) {
    return <p>Cargando detalles del equipo...</p>;
  }

  if (error) {
    return <p>{error}</p>;
  }

  if (!teamDetails) {
    return <p>No se encontraron detalles para este equipo.</p>;
  }

  const { nameTeam, descriptionTeam, teamLeaderDTO, regularUsers } = teamDetails;

  return (
    <div className="p-6 space-y-6">
      {/* Banner del Equipo */}
      <TeamBanner name={nameTeam} uuid={uuid} />

      {/* Detalles del Equipo */}
      <TeamDetailsCard
        description={descriptionTeam}
        leader={`${teamLeaderDTO?.name || ""} ${teamLeaderDTO?.surname || ""}`}
      />

      {/* Miembros del Equipo */}
      <TeamMembersList members={regularUsers || []} />
    </div>
  );
};

export default TeamDetails;
