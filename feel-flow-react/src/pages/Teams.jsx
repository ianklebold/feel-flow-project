import React, { useState, useEffect } from "react";
import { Helmet } from "react-helmet";
import { useNavigate } from "react-router-dom";
import { GetEquipos } from "../services/GetEquipos";
import Searchbar from "../components/Searchbar";
import Table from "../components/Table";
import Button from "../components/Button";
import Popup from "../components/Popup";
import Form from "../components/Form";
import { createTeam } from "../services/Teams/CreateTeam";
import { FaPlus, FaEye, FaEyeSlash } from 'react-icons/fa';


function Teams() {
  const [teams, setTeams] = useState([]);
  const [searchTeams, setSearchTeams] = useState("");
  const [searchLeaders, setSearchLeaders] = useState("");
  const navigate = useNavigate();
  const [newTeamPopup, setNewTeamIsOpen] = useState(false);
  const [formData, setFormData] = useState({
      nameTeam: "",
      descriptionTeam: "",
      nameTL: "",
      surnameTL: "",
      usernameTL: "",
      passwordTL: "",
  });
  const [errors, setErrors] = useState({}); // Error por cada input
  const [success, setSuccess] = useState(); // Éxito de la respuesta de la API
  const [apiError, setError] = useState(); // Error de la respuesta de la API
  const [showPassword, setShowPassword] = useState(false);

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  }

  useEffect(() => {
    const fetchTeams = async () => {
      const token = sessionStorage.getItem("token");
      if (!token) {
        console.error("Token no encontrado. Redirigiendo al login.");
        navigate("/login");
        return;
      }

      try {
        const response = await GetEquipos(token);
        console.log("Equipos obtenidos:", response);

        const formattedTeams = response.map((team) => ({
          uuid: team.uuid,
          teamName: team.nameTeam || "Sin nombre",
          leaderName: team.teamLeaderDTO
            ? `${team.teamLeaderDTO.name} ${team.teamLeaderDTO.surname}`.trim()
            : "Sin líder",
        }));

        setTeams(formattedTeams);
      } catch (error) {
        console.error("Error al obtener los equipos:", error);
      }
    };

    fetchTeams();
  }, [navigate]);

  const filteredTeams = teams.filter((team) => {
    const teamName = team.teamName || "";
    const leaderName = team.leaderName || "";
    return (
      teamName.toLowerCase().includes(searchTeams.toLowerCase()) &&
      leaderName.toLowerCase().includes(searchLeaders.toLowerCase())
    );
  });

  const columns = [
    { header: "Equipo", key: "teamName" },
    { header: "Team Leader", key: "leaderName" },
  ];

  const handleIconClick = (row) => {
    sessionStorage.setItem("teamID", row.uuid)
    navigate(`/teams/${row.teamName}`);
  };

  const openPopup = (event) => {
          event.preventDefault();
          setNewTeamIsOpen(true);
      };
  
      const handleChange = (e) => {
          const { name, value } = e.target;
          setFormData({
              ...formData,
              [name]: value,
          });
  
          if (errors[name]) {
              setErrors({ ...errors, [name]: null });
          }
      };
      const keyMapping = {
          "teamLeaderDTO.name": "nameTL",
          "teamLeaderDTO.surname": "surnameTL",
          "teamLeaderDTO.username": "usernameTL",
          "teamLeaderDTO.password": "passwordTL",
          "descriptionTeam": "descriptionTeam",
          "nameTeam": "nameTeam",
      };
      
      const handleSubmit = async (event) => {
          event.preventDefault();
          console.log("Formulario enviado");
          console.log(formData.surnameTL)
          
          try {
              const result = await createTeam(
                  formData.nameTeam,
                  formData.descriptionTeam,
                  formData.nameTL,
                  formData.surnameTL,
                  formData.usernameTL,
                  formData.passwordTL
              );
              console.log(result);
              if (result) {
                  const formattedErrors = result.errors.reduce((acc, curr) => {
                      const apiKey = Object.keys(curr)[0]; // Clave devuelta por la API
                      const inputKey = keyMapping[apiKey] || apiKey; // Mapear a la clave del input
                      acc[inputKey] = curr[apiKey]; // Asignar el mensaje de error
                      return acc;
                  }, {});
                  setErrors(formattedErrors);
              } else {
                  setSuccess("Equipo creado correctamente");
              }
          } catch (error) {
              console.error("Error al crear el equipo:", error);
          }
      };

  return (
    <>
      <Helmet>
        <title>Teams</title>
      </Helmet>
      <div className="p-6">
        <div className="flex flex-col md:flex-row gap-6 mt-6 items-center">
          <div className="flex-1">
            <Searchbar
              placeholder="Buscar equipos..."
              value={searchTeams}
              onChange={(e) => setSearchTeams(e.target.value)}
            />
          </div>
          <div className="flex-1">
            <Searchbar
              placeholder="Buscar team leaders..."
              value={searchLeaders}
              onChange={(e) => setSearchLeaders(e.target.value)}
            />
          </div>
          <div className="flex-2">
            <Button
              label="Crear Equipos"
              onClick={openPopup}
              color="blue"
              size="md"
              variant="solid"
              icon={FaPlus}
            />
            <Popup
              isOpen={newTeamPopup}
              title="Crear nuevo Equipo"
              // message="Crear un equipo nuevo"
              buttons={[
                {
                  label: "Aceptar",
                  onClick: () => { setNewTeamIsOpen(false) },
                  color: "blue",
                },
                {
                  label: "Cancelar",
                  onClick: () => { setNewTeamIsOpen(false) },
                  color: "red",
                },
              ]}
            >
              <div>
                <Form
                  handleSubmit={handleSubmit}
                  inputs={[
                    {
                      label: "Nombre del Equipo",
                      labelClass: "login",
                      name: "nameTeam", // Ajusta al nombre correcto en formData
                      value: formData.nameTeam,
                      onChange: handleChange, // Pasa solo handleChange
                      placeholder: "Ingresa el nombre del equipo",
                      color: "blue",
                      error: errors.nameTeam
                    },
                    {
                      label: "Descripción",
                      labelClass: "login",
                      name: "descriptionTeam",
                      value: formData.descriptionTeam,
                      onChange: handleChange,
                      placeholder: "Ingresa la descripción del equipo",
                      color: "blue",
                      error: errors.descriptionTeam,
                    },
                    {
                      label: "Nombre Team Leader",
                      labelClass: "login",
                      name: "nameTL",
                      value: formData.nameTL,
                      onChange: handleChange,
                      placeholder: "Ingresa el nombre del líder del equipo",
                      color: "blue",
                      error: errors.nameTL
                    },
                    {
                      label: "Apellido del Team Leader",
                      labelClass: "login",
                      name: "surnameTL",
                      value: formData.surnameTL,
                      onChange: handleChange,
                      placeholder: "Ingresa el apellido del líder del equipo",
                      color: "blue",
                      error: errors.surnameTL
                    },
                    {
                      label: "Username del Team Leader",
                      labelClass: "login",
                      name: "usernameTL",
                      value: formData.usernameTL,
                      onChange: handleChange,
                      placeholder: "Ingresa el username del líder del equipo",
                      color: "blue",
                      error: errors.usernameTL
                    },
                    {
                      label: "Password del Team Leader",
                      labelClass: "login",
                      name: "passwordTL",
                      value: formData.passwordTL,
                      type: showPassword ? "text" : "password",
                      onChange: handleChange,
                      placeholder: "Ingresa la nueva password del líder del equipo",
                      color: "blue",
                      error: errors.passwordTL,
                      icon: showPassword ? FaEyeSlash : FaEye,
                      onIconClick: togglePasswordVisibility,
                    },
                  ]}
                  buttons={[
                    {
                      label: "Guardar",
                      type: "submit",
                      color: "green",
                      variant: "solid",
                      className: "w-full",
                    },
                  ]}
                  error={apiError}
                  success={success}
                />
              </div>
            </Popup>
          </div>
        </div>
        <Table
          columns={columns}
          data={filteredTeams}
          showIcon={true}
          onIconClick={handleIconClick}
          striped={true}
          noDataText="No se encontraron equipos."
        />
      </div>
    </>
  );
}

export default Teams;
