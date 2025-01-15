import { useState, useEffect } from "react";
import TextInput from "../components/TextInput"; // Componente reutilizable
import { GetEquipobyID } from "../services/GetEquiposId"
import { UpdateEquipo } from "../services/UpdateEquipo"; // Servicios proporcionados

const EditTeam = ({ teamId, token }) => {
  const [formData, setFormData] = useState({
    teamName: "",
    teamDescription: "",
    leadName: "",
    leadSurname: "",
    leadEmail: "",
    leadPassword: "",
  });

  const [isSaving, setIsSaving] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  // Obtener los datos del equipo al cargar el componente
  useEffect(() => {
    const fetchTeamData = async () => {
      try {
        const data = await GetEquipobyID(token, teamId);
        console.log("Datos del equipo recibidos:", data);

        // Mapear los datos recibidos al formato del formulario
        setFormData({
          teamName: data.nameTeam || "",
          teamDescription: data.descriptionTeam || "",
          leadName: data.teamLead?.name || "",
          leadSurname: data.teamLead?.surname || "",
          leadEmail: data.teamLead?.email || "",
          leadPassword: "", // No se debe rellenar por seguridad
        });
      } catch (error) {
        console.error("Error al obtener los datos del equipo:", error);
        setError("No se pudieron cargar los datos del equipo.");
      }
    };

    fetchTeamData();
  }, [teamId, token]);

  // Manejar cambios en los inputs
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // Manejar el envío del formulario
  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSaving(true);
    setError(null);
    setSuccess(false);

    try {
      const success = await UpdateEquipo(
        teamId,
        token,
        formData.teamName,
        formData.teamDescription
      );

      if (success) {
        setSuccess(true);
      } else {
        setError("Error al actualizar el equipo.");
      }
    } catch (error) {
      console.error("Error al actualizar el equipo:", error);
      setError("Error al actualizar el equipo.");
    }

    setIsSaving(false);
  };

  return (
    <div className="max-w-lg mx-auto bg-white p-6 rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-4 text-center">Editar Equipo</h2>

      {/* Mensajes de error y éxito */}
      {error && (
        <div className="bg-red-100 text-red-800 p-3 rounded mb-4">
          {error}
        </div>
      )}
      {success && (
        <div className="bg-green-100 text-green-800 p-3 rounded mb-4">
          Equipo actualizado exitosamente.
        </div>
      )}

      <form onSubmit={handleSubmit}>
        {/* Sección Datos del Equipo */}
        <div className="mb-6">
          <h3 className="text-lg font-semibold mb-2">Datos del Equipo</h3>
          <TextInput
            label="Nombre del Equipo"
            name="teamName"
            value={formData.teamName}
            onChange={handleChange}
            placeholder="Ingrese el nombre del equipo"
            size="md"
            color="blue"
            required
          />
          <TextInput
            label="Descripción del Equipo"
            name="teamDescription"
            value={formData.teamDescription}
            onChange={handleChange}
            placeholder="Ingrese la descripción del equipo"
            size="md"
            color="blue"
            required
          />
        </div>

        {/* Sección Datos del Team Lead */}
        <div className="mb-6">
          <h3 className="text-lg font-semibold mb-2">Datos del Team Lead</h3>
          <TextInput
            label="Nombre"
            name="leadName"
            value={formData.leadName}
            onChange={handleChange}
            placeholder="Ingrese el nombre del Team Lead"
            size="md"
            color="blue"
            required
          />
          <TextInput
            label="Apellido"
            name="leadSurname"
            value={formData.leadSurname}
            onChange={handleChange}
            placeholder="Ingrese el apellido del Team Lead"
            size="md"
            color="blue"
            required
          />
          <TextInput
            label="Correo"
            name="leadEmail"
            value={formData.leadEmail}
            onChange={handleChange}
            placeholder="Ingrese el correo del Team Lead"
            type="email"
            size="md"
            color="blue"
            required
          />
          <TextInput
            label="Contraseña"
            name="leadPassword"
            value={formData.leadPassword}
            onChange={handleChange}
            placeholder="Ingrese una nueva contraseña"
            type="password"
            size="md"
            color="blue"
          />
        </div>

        {/* Botón de guardar */}
        <button
          type="submit"
          className={`w-full py-2 px-4 bg-blue-500 text-white rounded ${
            isSaving ? "opacity-50 cursor-not-allowed" : "hover:bg-blue-600"
          }`}
          disabled={isSaving}
        >
          {isSaving ? "Guardando..." : "Guardar Cambios"}
        </button>
      </form>
    </div>
  );
};

export default EditTeam;
