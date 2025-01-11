import React, { useState, useEffect } from "react";
import { Helmet } from "react-helmet";
import { useNavigate } from "react-router-dom";
import { GetEquipos } from "../services/GetEquipos";
import Searchbar from "../components/Searchbar";
import Table from "../components/Table";

function Teams() {
  const [teams, setTeams] = useState([]);
  const [searchTeams, setSearchTeams] = useState("");
  const [searchLeaders, setSearchLeaders] = useState("");
  const navigate = useNavigate();

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
    const selectedTeamUUID = row.uuid;
    console.log("Redirigiendo al equipo con UUID:", selectedTeamUUID);
    navigate(`/teams/${selectedTeamUUID}`);
  };

  return (
    <>
      <Helmet>
        <title>Teams</title>
      </Helmet>
      <div className="p-6">
        <div className="flex flex-col md:flex-row gap-6 mt-6">
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
