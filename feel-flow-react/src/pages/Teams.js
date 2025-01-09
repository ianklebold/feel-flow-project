import React, { useState, useEffect } from "react";
import { Helmet } from 'react-helmet';
import { useNavigate } from "react-router-dom"; // Importamos useNavigate
import { GetEquipos } from "../services/GetEquipos"; // Importa la función GetEquipos
import Searchbar from "../components/Searchbar";
import Table from "../components/Table";
import Button from "../components/Button";

function Teams() {
    const [teams, setTeams] = useState([]);
    const [searchTeams, setSearchTeams] = useState("");
    const [searchLeaders, setSearchLeaders] = useState("");
    const navigate = useNavigate(); // Usamos useNavigate para la navegación

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

    // Navegación al hacer clic en "Crear Equipos"
    const handleCreateTeam = () => {
        navigate("/edit-equipo"); // Cambia a la página "EditEquipo"
    };

    return (
        <div>
            <Helmet>
                <title>Teams</title>
            </Helmet>

            <div className="p-6">
                <div className="mb-4">
                    <Button
                        label="Crear Equipos"
                        onClick={handleCreateTeam} // Vinculamos el botón a la navegación
                        color="blue"
                        size="md"
                        variant="solid"
                    />
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
        </div>
    );
}

export default Teams;
