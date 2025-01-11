import { useState } from "react";

const useConfigurarNikoNikoModulo = (
  initialTeams,
  handleSubmit,
  openAlertPopup
) => {
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
  const [nikoNikoTeams, setNikoNikoTeams] = useState(initialTeams);

  // Abre el popup de configuración de Niko Niko
  const handleNikoNikoPopupOpen = () => {
    setIsNikoNikoPopupOpen(true);
  };

  // Cierra el popup de configuración de Niko Niko
  const handleNikoNikoPopupClose = () => {
    setIsNikoNikoPopupOpen(false);
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

    const nikoNikoStartDateTime = new Date(`${nikoNikoStartDate}T${nikoNikoStartTime}`);
    const nikoNikoEndDateTime = new Date(`${nikoNikoEndDate}T${nikoNikoEndTime}`);

    if (nikoNikoStartDateTime >= nikoNikoEndDateTime) {
      newErrors.nikoNikoStartDate =
        "La fecha y hora de inicio deben ser anteriores a la fecha y hora de fin.";
      newErrors.nikoNikoEndDate =
        "La fecha y hora de fin deben ser posteriores a la fecha y hora de inicio.";
    }

    setNikoNikoErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Maneja el envío del formulario de configuración de Niko Niko
  const handleNikoNikoFormSubmit = () => {
    if (validateNikoNikoForm()) {
      handleSubmit(selectedNikoNikoTeam, nikoNikoStartDate, nikoNikoStartTime, nikoNikoEndDate, nikoNikoEndTime);
      setIsNikoNikoPopupOpen(false); // Cierra el popup después de enviar el formulario
    } else {
      openAlertPopup({
        title: "Error en la configuración de Niko Niko",
        message: "Por favor, corrige los errores del formulario.",
        icon: "error",
        buttons: [
          {
            label: "Aceptar",
            onClick: () => setIsAlertPopupOpen(false),
            color: "red",
          },
        ],
      });
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
  };
};

export default useConfigurarNikoNikoModulo;
