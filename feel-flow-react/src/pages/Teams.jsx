import React, { useState, useEffect } from "react";
import { Helmet } from 'react-helmet';
import { GetEquipos } from "../services/GetEquipos"; // Importa la función GetEquipos
import Searchbar from "../components/Searchbar";
import Table from "../components/Table";
import Button from "../components/Button";
import Popup from "../components/Popup";
import Form from "../components/Form";
import { FaExclamationCircle } from "react-icons/fa";

function Teams() {
    const [teams, setTeams] = useState([]);
    const [searchTeams, setSearchTeams] = useState("");
    const [searchLeaders, setSearchLeaders] = useState("");
    const [newTeamPopup, setNewTeamIsOpen] = useState(false);


    useEffect(() => {
        const fetchTeams = async () => {
            try {
                const token = sessionStorage.getItem("token"); // Recupera el token de sessionStorage
                if (!token) {
                    console.error("Token no encontrado en sessionStorage.");
                    return;
                }

                // Llama a la función GetEquipos
                const response = await GetEquipos(token);
                console.log("Respuesta de la API GetEquipos:", response);

                // Ajusta los datos para la tabla
                const formattedTeams = response.map((team) => ({
                    teamName: team.nameTeam || "Sin nombre", // Nombre del equipo
                    leaderName: team.teamLeaderDTO
                        ? `${team.teamLeaderDTO.name} ${team.teamLeaderDTO.surname}`.trim()
                        : "Sin líder", // Nombre completo del líder
                }));

                setTeams(formattedTeams); // Almacena los datos formateados
            } catch (error) {
                console.error("Error al obtener los equipos:", error);
            }
        };

        fetchTeams();
    }, []);

    // Filtrar equipos según la búsqueda
    const filteredTeams = teams.filter(team => {
        const teamName = team.teamName || ""; // Asegurarse de que no sea undefined
        const leaderName = team.leaderName || ""; // Asegurarse de que no sea undefined
        return (
            teamName.toLowerCase().includes(searchTeams.toLowerCase()) &&
            leaderName.toLowerCase().includes(searchLeaders.toLowerCase())
        );
    });

    // Columnas de la tabla: Solo Equipo y Team Leader
    const columns = [
        { header: "Equipo", key: "teamName" },
        { header: "Team Leader", key: "leaderName" },
    ];

    // Acción al hacer clic en el icono
    const handleIconClick = (row) => {
        console.log("Clic en el icono de:", row.teamName);
    };

    const togglePopup = () => {
        setNewTeamIsOpen(!newTeamPopup);
    };

    const closePopup = () => {
        setNewTeamIsOpen(false);
    };

    const [nameTeam, setNameTeam] = useState();
    const [descriptionTeam, setDescriptionTeam] = useState();
    const [nameTeamLeader, setNameTeamLeader] = useState();
    const [surnameTeamLeader, setSurnameTeamLeader] = useState();
    const [usernameTeamLeader, setUsernameTeamLeader] = useState();
    const [passwordTeamLeader, setPasswordTeamLeader] = useState();
    const [error, setError] = useState();


    const newTeamInputs = [
        {
            label: "Nombre del Equipo",
            labelClass: "login",
            value: nameTeam,
            onChange: (e) => setNameTeam(e.target.value),
            placeholder: "Ingresa el nombre del equipo",
            color: "blue",
        },
        {
            label: "Descripcion",
            labelClass: "login",
            value: descriptionTeam,
            onChange: (e) => setDescriptionTeam(e.target.value),
            placeholder: "Ingresa la descripcion del equipo",
            color: "blue",
        },
        {
            label: "Nombre Team Leader",
            labelClass: "login",
            value: nameTeamLeader,
            onChange: (e) => setNameTeamLeader(e.target.value),
            placeholder: "Ingresa el nombre del lider del equipo",
            color: "blue",
        },
        {
            label: "Apellido del Team Leader",
            labelClass: "login",
            value: surnameTeamLeader,
            onChange: (e) => setSurnameTeamLeader(e.target.value),
            placeholder: "Ingresa el apellido del lider del equipo",
            color: "blue",
        },
        {
            label: "Username del Team Leader",
            labelClass: "login",
            value: usernameTeamLeader,
            onChange: (e) => setUsernameTeamLeader(e.target.value),
            placeholder: "Ingresa el username del lider del equipo",
            color: "blue",
        },
        {
            label: "Password del Team Leader",
            labelClass: "login",
            value: passwordTeamLeader,
            type: "password",
            onChange: (e) => setPasswordTeamLeader(e.target.value),
            placeholder: "Ingresa la nueva password del lider del equipo",
            color: "blue",
        },
    ];

    const newTeamButtons = [
        {
            label: "Guardar",
            type: "submit",
            color: "green",
            variant: "solid",
            className: "w-full",
        },
    ];

    const handleSubmit = async (e) => {
        // e.preventDefault();
        console.log(e)
    };

    return (
        <>
            <Helmet>
                <title>Teams</title>
            </Helmet>

            <div className="p-6">
                <div className="mb-4">
                    <Button
                        label="Crear Equipos"
                        onClick={togglePopup}
                        color="blue"
                        size="md"
                        variant="solid"
                    />
                    <Popup
                        isOpen={newTeamPopup}
                        title="Crear nuevo Equipo"
                        // message="Crear un equipo nuevo"
                        buttons={[
                            {
                                label: "Aceptar",
                                onClick: () => {setNewTeamIsOpen(false)},
                                color: "blue",
                            },
                            {
                                label: "Cancelar",
                                onClick: () => {setNewTeamIsOpen(false)},
                                color: "red",
                            },
                        ]}
                    >
                        <div>
                            <Form
                                // onSubmit={ }
                                inputs={newTeamInputs}
                                buttons={newTeamButtons}
                                error={error}

                            />
                        </div>
                    </Popup>

                </div>

                <div className="flex flex-col md:flex-row gap-6 mt-6">
                    <div className="flex-1">
                        <Searchbar
                            placeholder="Buscar equipos..."
                            value={searchTeams}
                            onChange={(e) => setSearchTeams(e.target.value)}
                            onSearch={() => console.log("Buscando equipos:", searchTeams)}
                            className="w-full border border-2 border-secondary rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div className="flex-1">
                        <Searchbar
                            placeholder="Buscar team leaders..."
                            value={searchLeaders}
                            onChange={(e) => setSearchLeaders(e.target.value)}
                            onSearch={() => console.log("Buscando team leaders:", searchLeaders)}
                            className="w-full border border-2 border-secondary rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
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
