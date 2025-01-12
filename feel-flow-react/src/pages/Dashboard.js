import React, { useState, useEffect } from "react";
import { Helmet } from "react-helmet";
import { renderChart } from "../components/Chart";

function Dashboard() {
  const [teamData, setTeamData] = useState(null);
  const [selectedMember, setSelectedMember] = useState(null);
  const [selectedSprint, setSelectedSprint] = useState("current");

  // Mock de datos directamente en el componente
  const fetchMockData = async () => {
    return {
      teamName: "Equipo Ágil",
      members: [
        {
          name: "Juan Pérez",
          responses: [4, 5, 4, 3, 4, 5, 5, 4, 5, 4, 4, 5],
        },
        {
          name: "Ana López",
          responses: [5, 4, 3, 4, 5, 4, 4, 3, 4, 3, 5, 4],
        },
      ],
      averages: [4.5, 4.5, 3.5, 3.5, 4.5, 4.5, 4.5, 3.5, 4.5, 3.5, 4.5, 4.5],
    };
  };

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetchMockData();
      setTeamData(response);
    };
    fetchData();
  }, []);

  if (!teamData) return <p>Cargando...</p>;

  const selectedData =
    selectedMember === null || selectedMember === ""
      ? teamData.averages
      : teamData.members[parseInt(selectedMember, 10)]?.responses || [];

  return (
    <div>
      <Helmet>
        <title>Dashboard</title>
      </Helmet>

      <div className="min-h-screen bg-gray-100 p-6">
        <div className="bg-white p-6 rounded-lg shadow-lg">
          <h1 className="text-2xl font-bold mb-4">12 pasos de la felicidad - {teamData.teamName}</h1>
          <div className="flex justify-start items-center mb-6 gap-4">
            <select
              id="member-select"
              className="p-2 border border-gray-300 rounded"
              onChange={(e) => setSelectedMember(e.target.value)}
            >
              <option value="">Promedio del Equipo</option>
              {teamData.members.map((member, index) => (
                <option key={index} value={index}>{member.name}</option>
              ))}
            </select>
            <select
              id="sprint-select"
              className="p-2 border border-gray-300 rounded"
              onChange={(e) => setSelectedSprint(e.target.value)}
            >
              <option value="current">Sprint Actual</option>
              <option value="previous">Sprint Anterior</option>
              <option value="twoAgo">Hace Dos Sprints</option>
            </select>
          </div>
          {renderChart(selectedData)}
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
