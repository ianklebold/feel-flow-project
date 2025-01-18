import { useState } from "react";

const useConfigurarKudosModulo = (authority, token, idTeam, openAlertPopup) => {
  // Estados
  const [isKudosPopupOpen, setIsKudosPopupOpen] = useState(false);
  const [kudosErrors, setKudosErrors] = useState({});
  const [selectedKudosTeam, setSelectedKudosTeam] = useState("");
  const [kudosStartDate, setKudosStartDate] = useState("");
  const [kudosEndDate, setKudosEndDate] = useState("");
  const [kudosStartTime, setKudosStartTime] = useState("");
  const [kudosEndTime, setKudosEndTime] = useState("");
  const [kudosDailyLimit, setKudosDailyLimit] = useState(1);

  const kudosStartDateTime = `${kudosStartDate}T${kudosStartTime}:00.000Z`;
  const kudosEndDateTime = `${kudosEndDate}T${kudosEndTime}:00.000Z`;

  // Simular la creación del módulo Kudos
  const crearModuloSimulado = () => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve("Module created successfully");
      }, 2000); // Simula una demora de 2 segundos
    });
  };

  // Abre el popup de configuración de Kudos
const handleKudosPopupOpen = () => {
    setIsKudosPopupOpen(true);
  };
  
  // Cierra el popup de configuración de Kudos
  const handleKudosPopupClose = () => {
    setIsKudosPopupOpen(false);
  };
  
  const handleCrearModuloKudos = async () => {
    if (authority === "TEAM_LEADER" || authority === "ADMIN") {
      setIsKudosPopupOpen(true); // Mostrar el popup de configuración
    } else {
      console.error("Permiso denegado: Solo el Team Leader puede abrir el módulo");
      openAlertPopup(
        "Permiso denegado",
        "Solo el Team Leader puede abrir el módulo",
        "warning"
      );
    }
  };

  // Validar el formulario de configuración de Kudos
  const validateKudosForm = () => {
    const newErrors = {};

    if (!selectedKudosTeam) {
      newErrors.selectedKudosTeam = "Por favor, selecciona un equipo.";
    }

    if (!kudosStartDate || !kudosStartTime) {
      newErrors.kudosStartDate = "Por favor, selecciona la fecha y/o la hora de inicio.";
    }

    if (!kudosEndDate || !kudosEndTime) {
      newErrors.kudosEndDate = "Por favor, selecciona la fecha y/o la hora de fin.";
    }

    if (!kudosDailyLimit || kudosDailyLimit <= 0) {
      newErrors.kudosDailyLimit = "Por favor, ingresa un límite válido para reconocimientos diarios.";
    }

    if (kudosStartDateTime >= kudosEndDateTime) {
      newErrors.kudosStartDate =
        "La fecha y hora de inicio deben ser anteriores a la fecha y hora de fin.";
      newErrors.kudosEndDate =
        "La fecha y hora de fin deben ser posteriores a la fecha y hora de inicio.";
    }

    setKudosErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Maneja el envío del formulario de configuración de Kudos
  const handleKudosFormSubmit = async () => {
    if (validateKudosForm()) {
      try {
        // Simula la creación del módulo
        const result = await crearModuloSimulado();

        if (result === "Module created successfully") {
          openAlertPopup(
            "Éxito",
            "Se creó el módulo Kudos exitosamente",
            "success"
          );
        } else {
          console.error("Error al crear módulo:", result);
          openAlertPopup(
            "Error",
            `No se pudo crear el módulo Kudos: ${result}`,
            "error"
          );
        }
      } catch (error) {
        console.error("Error al crear módulo:", error);
        openAlertPopup(
          "Error",
          `No se pudo crear el módulo Kudos: ${error.message || error}`,
          "error"
        );
      } finally {
        setIsKudosPopupOpen(false);
      }
    }
  };

  return {
    // Valores de estado
    isKudosPopupOpen,
    setIsKudosPopupOpen,
    kudosErrors,
    selectedKudosTeam,
    kudosStartDate,
    kudosEndDate,
    kudosStartTime,
    kudosEndTime,
    kudosStartDateTime,
    kudosEndDateTime,
    kudosDailyLimit,

    // Funciones
    handleCrearModuloKudos,
    handleKudosFormSubmit,
    setSelectedKudosTeam,
    setKudosStartDate,
    setKudosStartTime,
    setKudosEndDate,
    setKudosEndTime,
    setKudosDailyLimit,
    handleKudosPopupClose,
    handleKudosPopupOpen,
  };
};

export default useConfigurarKudosModulo;
