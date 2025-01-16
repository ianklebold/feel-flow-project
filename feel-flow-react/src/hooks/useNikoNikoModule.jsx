import { useState } from "react";
import { crearModulo } from "../services/crearModuloNikoNiko.js";

const useConfigurarNikoNikoModulo = (authority, token, idTeam, openAlertPopup) => {
  // Estados
  const [isNikoNikoPopupOpen, setIsNikoNikoPopupOpen] = useState(false);
  const [isAlertPopupOpen, setIsAlertPopupOpen] = useState(false);
  const [alertPopupContent, setAlertPopupContent] = useState({});
  const [nikoNikoErrors, setNikoNikoErrors] = useState({});
  const [selectedNikoNikoTeam, setSelectedNikoNikoTeam] = useState("");
  const [nikoNikoStartDate, setNikoNikoStartDate] = useState("");
  const [nikoNikoEndDate, setNikoNikoEndDate] = useState("");
  const [nikoNikoStartTime, setNikoNikoStartTime] = useState("");
  const [nikoNikoEndTime, setNikoNikoEndTime] = useState("");
  const [nikoNikoTimeToResponseStartDay, setNikoNikoTimeToResponseStartDay] = useState("");
  const [nikoNikoTimeToResponseEndDay, setNikoNikoTimeToResponseEndDay] = useState("");
  const nikoNikostartDateTime = `${nikoNikoStartDate}T${nikoNikoStartTime}:00.000Z`;
  const nikoNikoendDateTime = `${nikoNikoEndDate}T${nikoNikoEndTime}:00.000Z`;
  const timeStartToResponse = `${nikoNikoTimeToResponseStartDay}:00.000Z`;
  const timeEndToResponse = `${nikoNikoTimeToResponseEndDay}:00.000Z`;
  const [nikoNikoTeams, setNikoNikoTeams] = useState("");


  const handleCrearModuloNikoNiko = async () => {
    if (authority === "TEAM_LEADER" || authority === "ADMIN") {
      setIsNikoNikoPopupOpen(true); // Mostrar el popup de configuración
    } else {
      console.error("Permiso denegado: Solo el Team Leader puede abrir el módulo");
      openAlertPopup(
        "Permiso denegado",
        "Solo el Team Leader puede abrir el módulo",
        "warning"
      );
    }
  };
  // Abre el popup de configuración de Niko Niko
  const handleNikoNikoPopupOpen = () => {
    setIsNikoNikoPopupOpen(true);
    console.log("Abriendo popup de configuración de Niko Niko");
  };

  // Cierra el popup de configuración de Niko Niko
  const handleNikoNikoPopupClose = () => {
    setIsNikoNikoPopupOpen(false);
    console.log("Cerrando popup de configuración de Niko Niko");
  };

  // Valida el formulario de configuración de Niko Niko
  const validateNikoNikoForm = () => {
    const newErrors = {};

    if (!selectedNikoNikoTeam) {
      newErrors.selectedNikoNikoTeam = "Por favor, selecciona un equipo.";
    }

    if (!nikoNikoStartDate || !nikoNikoStartTime) {
      newErrors.nikoNikoStartDate = "Por favor, selecciona la fecha y/o la hora de inicio.";
    }

    if (!nikoNikoEndDate || !nikoNikoEndTime) {
      newErrors.nikoNikoEndDate = "Por favor, selecciona la fecha y/o la hora de fin.";
    }

    if (!nikoNikoTimeToResponseStartDay ) {
      newErrors.nikoNikoEndDate = "Por favor, selecciona la hora de inicio para responder las emociones.";
    }
    
    if (!nikoNikoTimeToResponseEndDay ) {
      newErrors.nikoNikoEndDate = "Por favor, selecciona la hora de fin para responder las emociones.";
    }

    if (!nikoNikoTimeToResponseEndDay || !nikoNikoTimeToResponseStartDay) {
      newErrors.nikoNikoEndDate = "Por favor, selecciona la hora de inicio y fin para responder las emociones.";
    }

    if (nikoNikostartDateTime >= nikoNikoendDateTime) {
      newErrors.nikoNikoStartDate =
        "La fecha y hora de inicio deben ser anteriores a la fecha y hora de fin.";
      newErrors.nikoNikoEndDate =
        "La fecha y hora de fin deben ser posteriores a la fecha y hora de inicio.";
    }

    if (nikoNikoTimeToResponseStartDay >= nikoNikoTimeToResponseEndDay) {
      newErrors.nikoNikoTimeToResponseStartDay =
        "La hora de inicio para responder las emociones debe ser anterior a la hora de fin.";
      newErrors.nikoNikoTimeToResponseEndDay =
        "La hora de fin para responder las emociones debe ser posterior a la hora de inicio.";
    }

    setNikoNikoErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Maneja el envío del formulario de configuración de Niko Niko
  const handleNikoNikoFormSubmit = () => {
    if (validateNikoNikoForm()) {
      try {
        const result = crearModulo(token, idTeam, nikoNikostartDateTime, nikoNikoendDateTime, timeStartToResponse, timeEndToResponse);
  
        if (result === "Module created successfully") {
          console.log("Módulo Niko Niko creado exitosamente");
          openAlertPopup(
            "Éxito",
            "Se creó el módulo Niko Niko exitosamente",
            "success"
          );
        } else {
          console.error("Error al crear módulo:", result);
          openAlertPopup(
            "Error",
            `No se pudo crear el módulo Niko Niko: ${result}`,
            "error"
          );
        }
      } catch (error) {
        console.error("Error al crear módulo:", error);
        openAlertPopup(
          "Error",
          "Ocurrió un error al intentar crear el módulo Niko Niko",
          "error"
        );
      } finally {
        setIsNikoNikoPopupOpen(false); // Cerrar popup de configuración
      }
    }
  };

  return {
    // Valores de estado
    isNikoNikoPopupOpen,
    setIsNikoNikoPopupOpen,
    isAlertPopupOpen,
    setIsAlertPopupOpen,
    alertPopupContent,
    setAlertPopupContent,
    nikoNikoErrors,
    selectedNikoNikoTeam,
    nikoNikoStartDate,
    nikoNikoEndDate,
    nikoNikoStartTime,
    nikoNikoEndTime,
    nikoNikostartDateTime,
    nikoNikoendDateTime,
    nikoNikoTimeToResponseStartDay,
    nikoNikoTimeToResponseEndDay,
    nikoNikoTeams,

    // Funciones
    handleNikoNikoPopupOpen,
    handleNikoNikoPopupClose,
    handleNikoNikoFormSubmit,
    setSelectedNikoNikoTeam,
    setNikoNikoStartDate,
    setNikoNikoStartTime,
    setNikoNikoEndDate,
    setNikoNikoEndTime,
    setNikoNikoTimeToResponseEndDay,
    setNikoNikoTimeToResponseStartDay,
    handleCrearModuloNikoNiko
  };
};

export default useConfigurarNikoNikoModulo;
