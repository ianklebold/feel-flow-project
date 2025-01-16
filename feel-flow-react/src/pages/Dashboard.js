import React, { useState, useEffect } from "react";
import { Helmet } from "react-helmet";
import { GetEquipos } from "../services/GetEquipos"; // Ajustar ruta si es necesario
import TeamSelector from "../components/TeamSelector";
import NikoNikoTable from "../components/NikoNikoTable";
import HappinessChart from "../components/HappinessChart";
import DashboardHeader from "../components/DashboardHeader";

function Dashboard() {
  const [teams, setTeams] = useState([]);
  const [selectedTeam, setSelectedTeam] = useState("");
  const [selectedMonth, setSelectedMonth] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [teamMembers, setTeamMembers] = useState([]);
  const [nikoData, setNikoData] = useState({});
  const [teamData, setTeamData] = useState(null);
  const [selectedMember, setSelectedMember] = useState(null);
  const [selectedSprint, setSelectedSprint] = useState("current");

  const months = [
    "Enero", "Febrero", "Marzo", "Abril",
    "Mayo", "Junio", "Julio", "Agosto",
    "Septiembre", "Octubre", "Noviembre", "Diciembre",
  ];

  useEffect(() => {
    const fetchTeams = async () => {
      const token = sessionStorage.getItem("token");
      if (!token) {
        console.error("Token no encontrado. Redirigiendo al login.");
        return;
      }

      try {
        setLoading(true);
        const response = await GetEquipos(token);
        setTeams(response);

        // Simulación de miembros y datos NicoNiko
        const mockTeamMembers = ["Juan", "María", "Pedro", "Lucía"];
        const mockNikoData = mockTeamMembers.reduce((acc, member) => {
          acc[member] = Array.from({ length: 30 }, () => Math.floor(Math.random() * 3) + 1);
          return acc;
        }, {});

        setTeamMembers(mockTeamMembers);
        setNikoData(mockNikoData);
      } catch (err) {
        console.error("Error al obtener los equipos:", err);
        setError("No se pudieron cargar los equipos. Inténtalo más tarde.");
      } finally {
        setLoading(false);
      }
    };

    fetchTeams();
  }, []);

  useEffect(() => {
    const fetchMockData = async () => {
      return {
        teamName: "Equipo Ágil",
        members: [
          { name: "Juan Pérez", responses: [4, 5, 4, 3, 4, 5, 5, 4, 5, 4, 4, 5] },
          { name: "Ana López", responses: [5, 4, 3, 4, 5, 4, 4, 3, 4, 3, 5, 4] },
        ],
        averages: [4.5, 4.5, 3.5, 3.5, 4.5, 4.5, 4.5, 3.5, 4.5, 3.5, 4.5, 4.5],
      };
    };

    const fetchData = async () => {
      const response = await fetchMockData();
      setTeamData(response);
    };

    fetchData();
  }, []);

  const selectedData =
    selectedMember === null || selectedMember === ""
      ? teamData?.averages || []
      : teamData?.members[parseInt(selectedMember, 10)]?.responses || [];

  return (
    <div className="min-h-screen bg-gradient-to-r from-indigo-500 via-purple-600 to-pink-500 py-6 px-8">
      <Helmet>
        <title>Dashboard</title>
      </Helmet>
      <div className="max-w-7xl mx-auto bg-white rounded-lg shadow-lg p-8">
        <DashboardHeader />
        {loading && <p className="text-blue-500 font-semibold text-center">Cargando equipos...</p>}
        {error && <p className="text-red-500 font-semibold text-center">{error}</p>}
        <TeamSelector
          teams={teams}
          selectedTeam={selectedTeam}
          setSelectedTeam={setSelectedTeam}
          months={months}
          selectedMonth={selectedMonth}
          setSelectedMonth={setSelectedMonth}
        />
        <NikoNikoTable teamMembers={teamMembers} nikoData={nikoData} />
        {teamData && (
          <HappinessChart
            teamData={teamData}
            selectedMember={selectedMember}
            setSelectedMember={setSelectedMember}
            selectedSprint={selectedSprint}
            setSelectedSprint={setSelectedSprint}
            selectedData={selectedData}
          />
        )}
      </div>
    </div>
  );
}

export default Dashboard;
