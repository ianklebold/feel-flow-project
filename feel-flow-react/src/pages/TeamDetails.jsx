import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { GetEquipobyID } from "../services/GetEquipoId";
import TeamBanner from "../components/TeamBanner";
import TeamDetailsCard from "../components/TeamDetailsCard";
import TeamMembersList from "../components/TeamMembersList";
import Popup from "../components/Popup";
import Form from "../components/Form";
import { getAuthData } from "../services/session";
import { UpdateEquipo } from "../services/UpdateEquipo";
import { GetIdEquipo } from "../services/GetEquipos.js";

const TeamDetails = () => {
  const { token } = getAuthData();
  const { teamId } = useParams();
  const navigate = useNavigate();
  const [teamDetails, setTeamDetails] = useState(null);
  const [editPopupOpen, setEditPopupOpen] = useState(false);
  const [formData, setFormData] = useState({
    nameTeam: "",
    descriptionTeam: "",
  });
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  const uuid = sessionStorage.getItem("teamID") || teamId;

  useEffect(() => {
    GetIdEquipo(token)
      .then((uuid) => {
        if (uuid) {
          sessionStorage.setItem("teamID", uuid);
        } else {
          console.error("UUID del equipo no proporcionado. Redirigiendo a equipos.");
          setError("No se pudo identificar el equipo.");
          navigate("/dashboard");
        }
      })
      .catch((error) => {
        console.error("Error al obtener los equipos:", error);
      });

  const fetchTeamDetails = async () => {

    if (!token) {
      setError("No se encontró el token. Redirigiendo al login.");
      setTimeout(() => navigate("/login"), 2000);
      return;
    }

    try {
      const data = await GetEquipobyID(token, uuid);
      setTeamDetails(data);
      setFormData({
        nameTeam: data.nameTeam,
        descriptionTeam: data.descriptionTeam,
      });
    } catch {
      setError("No se pudieron cargar los detalles del equipo.");
    } finally {
      setIsLoading(false);
    }
  };

  fetchTeamDetails();
}, [uuid, navigate]);

const handleUpdateTeam = async (event) => {
  event.preventDefault();
  const { token } = getAuthData();

  try {
    const success = await UpdateEquipo(uuid, token, formData.nameTeam, formData.descriptionTeam);
    if (success) {
      setTeamDetails((prev) => ({
        ...prev,
        nameTeam: formData.nameTeam,
        descriptionTeam: formData.descriptionTeam,
      }));
      setEditPopupOpen(false);
    }
  } catch {
    console.error("Error en la actualización del equipo");
  }
};

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
    <TeamBanner
      name={nameTeam}
      uuid={uuid}
      onEdit={() => setEditPopupOpen(true)}
    />

    <Popup
      isOpen={editPopupOpen}
      title="Editar Equipo"
      buttons={[]}
    >
      <Form
        handleSubmit={handleUpdateTeam}
        inputs={[
          {
            label: "Nombre del Equipo",
            name: "nameTeam",
            value: formData.nameTeam,
            placeholder: "Edita el nombre del equipo",
            onChange: (e) => setFormData({ ...formData, nameTeam: e.target.value }),
            color: "blue",
          },
          {
            label: "Descripción del Equipo",
            name: "descriptionTeam",
            value: formData.descriptionTeam,
            placeholder: "Edita la descripción del equipo",
            onChange: (e) => setFormData({ ...formData, descriptionTeam: e.target.value }),
            color: "blue",
          },
        ]}
        buttons={[
          {
            label: "Guardar",
            type: "submit",
            color: "green",
            className: "w-auto px-6",
          },
          {
            label: "Cerrar",
            onClick: () => setEditPopupOpen(false),
            color: "red",
            className: "w-auto px-6",
          },
        ]}
        customButtonsStyle="flex justify-center gap-4"
      />
    </Popup>

    <TeamDetailsCard
      description={descriptionTeam}
      leader={`${teamLeaderDTO?.name || ""} ${teamLeaderDTO?.surname || ""}`}
    />

    <TeamMembersList members={regularUsers || []} />
  </div>
);
};

export default TeamDetails;
