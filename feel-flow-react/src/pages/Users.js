import React, { useState, useEffect } from "react";
import { Helmet } from "react-helmet";
import SearchBar from "../components/Searchbar";
import Table from "../components/Table";

function Users() {
  const [users, setUsers] = useState([]); // Lista completa de usuarios
  const [filteredUsers, setFilteredUsers] = useState([]); // Usuarios filtrados
  const [searchTerm, setSearchTerm] = useState(""); // Término de búsqueda

  useEffect(() => {
    // Simulación del servicio que retorna usuarios
    const simulatedUsers = [
      { name: "John Doe", role: "Admin" },
      { name: "Jane Smith", role: "User" },
      { name: "Alice Johnson", role: "Editor" },
      { name: "Bob Brown", role: "Viewer" },
    ];

    console.log("Usuarios simulados cargados:", simulatedUsers);
    setUsers(simulatedUsers);
    setFilteredUsers(simulatedUsers); // Establece usuarios y filtrados al mismo tiempo
  }, []);

  useEffect(() => {
    console.log("Usuarios originales:", users);
    console.log("Término de búsqueda:", searchTerm);

    if (users.length > 0) {
      const filtered = users.filter((user) =>
        user.name.toLowerCase().includes(searchTerm.toLowerCase())
      );

      console.log("Usuarios después de aplicar el filtro:", filtered);
      setFilteredUsers(filtered);
    }
  }, [searchTerm, users]);

  const columns = [
    { header: "Nombre", key: "name" },
    { header: "Rol", key: "role" },
  ];

  return (
    <div>
      <Helmet>
        <title>Usuarios</title>
      </Helmet>

      <div className="p-6">
        <h1 className="text-2xl font-bold">Gestión de Usuarios</h1>
        <p>Aquí podrás gestionar a los usuarios de la plataforma.</p>

        <div className="my-6">
          <SearchBar
            placeholder="Buscar usuarios..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>

        <Table
          columns={columns}
          data={filteredUsers}
          noDataText="No se encontraron usuarios."
        />
      </div>
    </div>
  );
}

export default Users;
