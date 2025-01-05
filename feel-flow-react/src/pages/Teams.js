import React, { useState } from "react";
import { Helmet } from 'react-helmet';
import Searchbar from "../components/Searchbar";
import Table from "../components/Table";  // Importamos el componente Table
import Button from "../components/Button"; // Asegúrate de importar el componente Button

function Teams() {
    const [searchTeams, setSearchTeams] = useState("");
    const [searchLeaders, setSearchLeaders] = useState("");

    const handleSearchTeams = (query) => {
        console.log("Buscando equipos:", query);
    };

    const handleSearchLeaders = (query) => {
        console.log("Buscando team leaders:", query);
    };

    // Datos de ejemplo para la tabla de equipos y líderes, incluyendo iconos
    const teamsData = [
        { teamName: "Equipo A", leaderName: "Juan Pérez", iconUrl: "/path/to/iconA.png" },
        { teamName: "Equipo B", leaderName: "María Gómez", iconUrl: "/path/to/iconB.png" },
        { teamName: "Equipo C", leaderName: "Carlos López", iconUrl: "/path/to/iconC.png" },
        { teamName: "Equipo D", leaderName: "Ana Fernández", iconUrl: "/path/to/iconD.png" },
    ];

    // Filtrar equipos y líderes según los valores de búsqueda
    const filteredTeams = teamsData.filter(team => 
        team.teamName.toLowerCase().includes(searchTeams.toLowerCase()) &&
        team.leaderName.toLowerCase().includes(searchLeaders.toLowerCase())
    );

    // Definimos las columnas de la tabla en el orden correcto
    const columns = [
        { header: "Equipo", key: "team" },  // Columna para el icono y nombre del equipo
        { header: "Team Leader", key: "leaderName" },
    ];

    // Función para manejar clic en el icono
    const handleIconClick = (row) => {
        console.log("Clic en el icono de:", row.teamName);
        // Aquí puedes agregar lo que desees que pase cuando se haga clic en el icono
    };

    return (
        <div>
            <Helmet>
                <title>Teams</title>
            </Helmet>

            <div className="p-6">
                {/* Botón "Crear Equipos" */}
                <div className="mb-4">
                    <Button
                        label="Crear Equipos"
                        onClick={() => console.log("Crear Equipos Clicked")}
                        color="blue"
                        size="md"
                        variant="solid"
                    />
                </div>

                {/* Contenedor de las dos barras de búsqueda */}
                <div className="flex flex-col md:flex-row gap-6 mt-6">
                    {/* Bloque para Equipos */}
                    <div className="flex-1">
                        <div className="relative">
                            <label className="absolute -top-2 left-3 bg-light px-1 text-sm font-medium text-primary rounded-lg">
                                Equipo
                            </label>
                            <Searchbar
                                placeholder="Buscar equipos..."
                                value={searchTeams}
                                onChange={(e) => setSearchTeams(e.target.value)}
                                onSearch={() => handleSearchTeams(searchTeams)}
                                className="w-full border border-2 border-secondary rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                            />
                        </div>
                    </div>

                    {/* Bloque para Team Leaders */}
                    <div className="flex-1">
                        <div className="relative">
                            <label className="absolute -top-2 left-3 bg-light px-1 text-sm font-medium text-primary rounded-lg">
                                Team leader
                            </label>
                            <Searchbar
                                placeholder="Buscar team leaders..."
                                value={searchLeaders}
                                onChange={(e) => setSearchLeaders(e.target.value)}
                                onSearch={() => handleSearchLeaders(searchLeaders)}
                                className="w-full border border-2 border-secondary rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                            />
                        </div>
                    </div>
                    
                </div>

                {/* Llamada al componente Table y pasamos los parámetros */}
                <Table
                    columns={columns}
                    data={filteredTeams}
                    showIcon={true}  // Mostrar la columna de icono
                    onIconClick={handleIconClick}  // Acción al hacer clic en el icono
                    striped={true}
                    noDataText="No se encontraron equipos."
                />
            </div>
        </div>
    );
}

export default Teams;
