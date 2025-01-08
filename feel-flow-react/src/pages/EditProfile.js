import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";

function EditProfile() {
  const navigate = useNavigate();

  // Estados para la información editable
  const [formData, setFormData] = useState({
    name: "Nombre Ejemplo",
    email: "ejemplo@correo.com",
    company: "Empresa Ejemplo",
    team: "Equipo Ejemplo",
    role: "Administrador",
  });

  // Manejar cambios en los inputs
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSave = () => {
    Swal.fire({
      icon: "success",
      title: "Guardado",
      text: "La información del perfil ha sido actualizada exitosamente.",
    }).then(() => {
      navigate("/"); // Redirigir a la página del perfil
    });
  };
  

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="mx-auto w-11/12 max-w-4xl bg-white shadow-md rounded-lg p-6">
        <h2 className="text-lg font-semibold text-gray-800 mb-4">Editar Perfil</h2>
        <form className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">Nombre Completo</label>
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">Email</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">Empresa</label>
            <input
              type="text"
              name="company"
              value={formData.company}
              onChange={handleChange}
              className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">Equipo</label>
            <input
              type="text"
              name="team"
              value={formData.team}
              onChange={handleChange}
              className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">Rol</label>
            <input
              type="text"
              name="role"
              value={formData.role}
              onChange={handleChange}
              className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
            />
          </div>
        </form>

        <div className="mt-6 flex justify-end space-x-4">
          <button
            className="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400"
            onClick={() => navigate("/")}
          >
            Cancelar
          </button>
          <button
            className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"
            onClick={handleSave}
          >
            Guardar
          </button>
        </div>
      </div>
    </div>
  );
}

export default EditProfile;
