import React, { useState, useEffect } from "react";
import { Helmet } from "react-helmet";
import { GetEquipos } from "../services/GetEquipos"; // Ajustar ruta si es necesario

function Dashboard() {
  const [teams, setTeams] = useState([]);
  const [selectedTeam, setSelectedTeam] = useState("");
  const [selectedMonth, setSelectedMonth] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [teamMembers, setTeamMembers] = useState([]);
  const [nikoData, setNikoData] = useState({}); // Datos NicoNiko por miembro y día

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

        // Simulación de miembros del equipo y datos NicoNiko
        const mockTeamMembers = ["Juan", "María", "Pedro", "Lucía"];
        const mockNikoData = mockTeamMembers.reduce((acc, member) => {
          acc[member] = Array.from({ length: 30 }, () => Math.floor(Math.random() * 3) + 1); // Valores entre 1 y 3
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

  const handleTeamChange = (event) => {
    setSelectedTeam(event.target.value);
  };

  const handleMonthChange = (event) => {
    setSelectedMonth(event.target.value);
  };

  // Mapear valores a caritas con colores (con círculos)
  const getFaceWithColor = (value) => {
    let bgColor = "bg-gray-500"; // Por defecto gris neutro
    if (value === 1) {
      bgColor = "bg-red-500"; // Triste (rojo)
    } else if (value === 2) {
      bgColor = "bg-yellow-500"; // Neutro (amarillo)
    } else if (value === 3) {
      bgColor = "bg-green-500"; // Feliz (verde)
    }

    return (
      <div
        className={`${bgColor} w-8 h-8 rounded-full flex items-center justify-center text-white`}
      >
        {value === 1 ? "😢" : value === 2 ? "😐" : "😊"}
      </div>
    );
  };

  return (
    <div className="min-h-screen bg-gradient-to-r from-indigo-500 via-purple-600 to-pink-500 py-6 px-8">
      <Helmet>
        <title>Dashboard</title>
      </Helmet>

      <div className="max-w-7xl mx-auto bg-white rounded-lg shadow-lg p-8">
        <h1 className="text-4xl font-semibold text-gray-900 mb-6 text-center">Dashboard</h1>
        <p className="text-gray-600 mb-8 text-lg text-center">¡Bienvenido al panel principal!</p>

        {/* Mensajes de carga o error */}
        {loading && <p className="text-blue-500 font-semibold text-center">Cargando equipos...</p>}
        {error && <p className="text-red-500 font-semibold text-center">{error}</p>}

        {/* Controles de selección */}
        <div className="mt-8 grid gap-6 grid-cols-1 md:grid-cols-2">
          <div>
            <label htmlFor="teamSelect" className="block text-sm font-medium text-gray-700 mb-2">
              Seleccionar equipo
            </label>
            <select
              id="teamSelect"
              value={selectedTeam}
              onChange={handleTeamChange}
              className="block w-full p-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg"
            >
              <option value="">Todos los equipos</option>
              {teams.map((team) => (
                <option key={team.uuid} value={team.uuid}>
                  {team.nameTeam}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label htmlFor="monthSelect" className="block text-sm font-medium text-gray-700 mb-2">
              Seleccionar mes
            </label>
            <select
              id="monthSelect"
              value={selectedMonth}
              onChange={handleMonthChange}
              className="block w-full p-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg"
            >
              <option value="">Todos los meses</option>
              {months.map((month, index) => (
                <option key={index} value={index + 1}>
                  {month}
                </option>
              ))}
            </select>
          </div>
        </div>

        {/* Información seleccionada */}
        <div className="mt-6 text-gray-800 text-lg">
          <p><strong>Equipo seleccionado:</strong> {selectedTeam || "Ninguno"}</p>
          <p><strong>Mes seleccionado:</strong> {months[selectedMonth - 1] || "Todos"}</p>
        </div>

        {/* Gráfico NicoNiko */}
        <h2 className="text-2xl font-semibold text-gray-800 mb-6 text-center">Niko-Niko</h2>
        <div className="mt-8 overflow-x-auto bg-white rounded-lg shadow-lg p-6">
          {/* <h2 className="text-2xl font-semibold text-gray-800 mb-6 text-center">Gráfico Niko-Niko</h2> */}
          <table className="min-w-full text-sm table-auto border-separate border-spacing-0">
            <thead className="bg-gray-100">
              <tr>
                <th className="px-6 py-3 text-gray-600 font-semibold text-center">Miembro / Día</th>
                {Array.from({ length: 30 }, (_, i) => (
                  <th key={i} className="px-4 py-2 text-gray-600 text-center">{i + 1}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {teamMembers.map((member) => (
                <tr key={member} className="border-b">
                  <td className="px-6 py-4 font-medium text-gray-700 text-center">{member}</td>
                  {nikoData[member]?.map((value, i) => (
                    <td key={i} className="px-4 py-2 text-center">{getFaceWithColor(value)}</td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
