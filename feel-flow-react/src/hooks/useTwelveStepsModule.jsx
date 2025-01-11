import { useState } from "react";
import { crearModulo } from "../services/crearModuloTwelveSteps.js";

const useTwelveStepsModule = (authority, token, idTeam, openAlertPopup) => {
  const [isConfigPopupOpen, setIsConfigPopupOpen] = useState(false);
  const [errors, setErrors] = useState({});
  const [selectedTeam, setSelectedTeam] = useState("");
  const [selectedSet, setSelectedSet] = useState("");
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");

  const handleCrearModulo12Pasos = async () => {
    if (authority === "TEAM_LEADER") {
      setIsConfigPopupOpen(true); // Mostrar el popup de configuración
    } else {
      openAlertPopup(
        "Permiso denegado",
        "Solo el Team Leader puede abrir el módulo",
        "warning"
      );
    }
  };

  const handleConfigurarModulo = async () => {
    if (!selectedTeam || !selectedSet || !startDate || !endDate) {
      openAlertPopup(
        "Error de configuración",
        "Por favor, selecciona equipo, set de preguntas y fechas.",
        "error"
      );
      return;
    }

    try {
      const result = await crearModulo(token, idTeam);
      if (result === "Module created successfully") {
        openAlertPopup(
          "Éxito",
          "Se creó el módulo de 12 Pasos exitosamente",
          "success"
        );
      } else {
        openAlertPopup(
          "Error",
          `No se pudo crear el módulo de 12 Pasos: ${result}`,
          "error"
        );
      }
    } catch (error) {
      console.error("Error al crear módulo:", error);
      openAlertPopup(
        "Error",
        "Ocurrió un error al intentar crear el módulo",
        "error"
      );
    } finally {
      setIsConfigPopupOpen(false); // Cerrar popup de configuración
    }
  };

  const handleSubmit = () => {
    const newErrors = {};

    if (!selectedTeam) {
      newErrors.selectedTeam = "Por favor, selecciona un equipo.";
    }

    if (!selectedSet) {
      newErrors.selectedSet = "Por favor, selecciona un set de preguntas.";
    }

    if (!startDate || !startTime) {
      newErrors.startDate = "Por favor, selecciona la fecha y/o la hora de inicio.";
    }

    if (!endDate || !endTime) {
      newErrors.endDate = "Por favor, selecciona la fecha y/o la hora de fin.";
    }

    const startDateTime = new Date(`${startDate}T${startTime}`);
    const endDateTime = new Date(`${endDate}T${endTime}`);

    if (startDateTime >= endDateTime) {
      newErrors.startDate =
        "La fecha y hora de inicio deben ser anteriores a la fecha y hora de fin.";
      newErrors.endDate =
        "La fecha y hora de fin deben ser posteriores a la fecha y hora de inicio.";
    }

    setErrors(newErrors);

    // Si no hay errores, continúa
    if (Object.keys(newErrors).length === 0) {
      handleConfigurarModulo(); // Llama a la función principal
    }
  };

  return {
    isConfigPopupOpen,
    setIsConfigPopupOpen,
    errors,
    setErrors,
    selectedTeam,
    setSelectedTeam,
    selectedSet,
    setSelectedSet,
    startDate,
    setStartDate,
    endDate,
    setEndDate,
    startTime,
    setStartTime,
    endTime,
    setEndTime,
    handleCrearModulo12Pasos,
    handleConfigurarModulo,
    handleSubmit,
  };
};

export default useTwelveStepsModule;
