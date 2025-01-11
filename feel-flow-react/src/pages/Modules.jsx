import React, { useEffect, useState } from "react";
import { Helmet } from "react-helmet";
import Modulo from "../components/moduloAgil.jsx";
import Popup from "../components/Popup.jsx";
import { crearModulo } from "../services/crearModuloTwelveSteps.js";
import { GetIdEquipo } from "../services/GetEquipos.js";
import { getAuthData, getUserData } from "../services/session";

// Imágenes
import headerImage12pasos from "../img/twelve_steps_header.png";
import bodyImage12pasos from "../img/twelve_steps_body.png";
import footerImage12pasos from "../img/twelve_steps_footer.png";
import contentImage12pasos from "../img/twelve_steps-removebg.png";

import headerImageNikoNiko from "../img/niko_niko_header.png";
import bodyImageNikoNiko from "../img/niko_niko_body.png";
import footerImageNikoNiko from "../img/niko_niko_footer.png";
import contentImageNikoNiko from "../img/niko_niko_content.png";

import headerImageKudos from "../img/kudos_header.png";
import bodyImageKudos from "../img/kudos_body.png";
import footerImageKudos from "../img/kudos_footer.png";
import contentImageKudos from "../img/kudos_content.png";

function Modules() {
    const { token } = getAuthData();
    const  { authority } = getUserData();
    const [idTeam, setIdTeam] = useState("");
    const [isPopupOpen, setIsPopupOpen] = useState(false);
    const [isConfigPopupOpen, setIsConfigPopupOpen] = useState(false);
    const [popupContent, setPopupContent] = useState({
        title: "",
        message: "",
        icon: null,
        buttons: [],
    });
    
    // Estados para la configuración del módulo
    const [selectedTeam, setSelectedTeam] = useState(""); // Equipo seleccionado
    const [selectedSet, setSelectedSet] = useState(""); // Set de preguntas seleccionado
    const [startDate, setStartDate] = useState(null); // Fecha de inicio seleccionada
    const [endDate, setEndDate] = useState(null); // Fecha de fin seleccionada
    const [startTime, setStartTime] = useState(""); // Hora de inicio seleccionada
    const [endTime, setEndTime] = useState(""); // Hora de fin seleccionada
    const [teams, setTeams] = useState([]); // Lista de equipos
    const [setsPreguntas, setSetsPreguntas] = useState([]); // Lista de sets de preguntas
    const [errors, setErrors] = useState({
        selectedTeam: "",
        selectedSet: "",
        startDate: "",
        startTime: "",
        endDate: "",
        endTime: "",
    }); // Errores de validación


    useEffect(() => {
        // Simula la carga de datos desde un API
        setTeams([
            { id: "1", name: "Equipo A" },
            { id: "2", name: "Equipo B" },
            { id: "3", name: "Equipo C" },
        ]);

        setSetsPreguntas([
            { id: "1", name: "Set de Preguntas 1" },
            { id: "2", name: "Set de Preguntas 2" },
            { id: "3", name: "Set de Preguntas 3" },
        ]);
    }, []);

    useEffect(() => {
        // Obtener lista de equipos cuando se monta el componente
        GetIdEquipo(token)
            .then((uuid) => {
                if (uuid) {
                    console.log("UUID del equipo:", uuid);
                    setIdTeam(uuid); // Guarda el UUID directamente en idTeam
                }
            })
            .catch((error) => {
                console.error("Error al obtener los equipos:", error);
            });
    }, [token]);

    const handleCrearModulo = () => {
        console.log("handleCrearModulo fue llamado, pero no realiza ninguna acción.");
    };

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

        if (!startDate || !startTime ){
            newErrors.startDate = "Por favor, selecciona la fecha y/o la hora de inicio.";
        }

        if (!endDate || (!endTime)) {
            newErrors.endDate = "Por favor, selecciona la fecha y/o la hora de fin.";
        }

        const startDateTime = new Date(`${startDate}T${startTime}`);
        const endDateTime = new Date(`${endDate}T${endTime}`);

        if (startDateTime >= endDateTime ) {
            newErrors.startDate = "La fecha y hora de inicio deben ser anteriores a la fecha y hora de fin.";
            newErrors.endDate = "La fecha y hora de fin deben ser posteriores a la fecha y hora de inicio.";
        }

        setErrors(newErrors);

        // Si no hay errores, continúa
        if (Object.keys(newErrors).length === 0) {
            handleConfigurarModulo(); // Llama a la función principal
        }
    };

    const openAlertPopup = (title, message, type) => {
        const icons = {
            success: <span className="text-green-500 text-5xl mr-4">✔</span>,
            error: <span className="text-red-500 text-5xl mr-4">✖</span>,
            warning: <span className="text-yellow-400 text-5xl mr-4">⚠</span>,
        };

        setPopupContent({
            title,
            message,
            icon: icons[type],
            buttons: [
                {
                    label: "Aceptar",
                    onClick: () => setIsPopupOpen(false),
                    color: type === "success" ? "green" : "red",
                },
            ],
        });

        setIsPopupOpen(true);
    };

    return (
        <div>
            <Helmet>
                <title>Modules</title>
            </Helmet>

            <h1 className="text-3xl font-bold text-center my-2">Módulos Ágiles</h1>

            {/* Módulo 12 pasos */}
            <Modulo
                mostrarSinEncuestas={false}
                tituloModulo="12 PASOS DE LA FELICIDAD"
                imagenHeader={headerImage12pasos}
                imagenBody={bodyImage12pasos}
                imagenFooter={footerImage12pasos}
                imagenContent={contentImage12pasos}
                descripcionModulo="El objetivo de esta práctica es reflexionar sobre diferentes aspectos relacionados con la felicidad, cómo los incorporamos en nuestro día a día y poder generar acciones que impulsen nuestra felicidad."
                botones={[
                    {
                        texto: "Habilitar 12 Pasos",
                        onClick: handleCrearModulo12Pasos,
                        color: "light_pink",
                    },
                ]}
            />

            {/* Popup de alerta */}
            <Popup
                isOpen={isPopupOpen}
                title={popupContent.title}
                message={popupContent.message}
                icon={popupContent.icon}
                buttons={popupContent.buttons}
            />

            {/* Popup de configuración */}
            {isConfigPopupOpen && (
                <Popup
                    isOpen={isConfigPopupOpen}
                    title="Configurar Módulo 12 Pasos de la Felicidad"
                    message="Seleccione las opciones para configurar el módulo."
                    buttons={[
                        {
                            label: "Cancelar",
                            onClick: () => setIsConfigPopupOpen(false),
                            color: "red",
                        },
                        {
                            label: "Aceptar",
                            onClick: handleSubmit, // Valida las fechas, horas y otros campos
                            color: "blue",
                        },
                    ]}
                >
                    <div className="flex flex-col space-y-4">
                        {/* Selección de equipo */}
                        <div>
                            <label htmlFor="equipo" className="block text-gray-700 font-bold mb-2">
                                Seleccionar Equipo
                            </label>
                            <select
                                id="equipo"
                                value={selectedTeam}
                                onChange={(e) => setSelectedTeam(e.target.value)}
                                className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                            >
                                <option value="">Seleccione un equipo</option>
                                {teams.map((team) => (
                                    <option key={team.id} value={team.id}>
                                        {team.name}
                                    </option>
                                ))}
                            </select>
                            {errors.selectedTeam && (
                                <p className="text-red-500 text-sm mt-1">{errors.selectedTeam}</p>
                            )}
                        </div>

                        {/* Selección de set de preguntas */}
                        <div>
                            <label htmlFor="setPreguntas" className="block text-gray-700 font-bold mb-2">
                                Seleccionar Set de Preguntas
                            </label>
                            <select
                                id="setPreguntas"
                                value={selectedSet}
                                onChange={(e) => setSelectedSet(e.target.value)}
                                className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                            >
                                <option value="">Seleccione un set</option>
                                {setsPreguntas.map((set) => (
                                    <option key={set.id} value={set.id}>
                                        {set.name}
                                    </option>
                                ))}
                            </select>
                            {errors.selectedSet && (
                                <p className="text-red-500 text-sm mt-1">{errors.selectedSet}</p>
                            )}
                        </div>

                        {/* Selección de fechas */}
                        <div className="flex flex-col space-y-4">
                            {/* Selección de fecha de inicio */}
                            <div>
                                <label htmlFor="fechaInicio" className="block text-gray-700 font-bold mb-2">
                                    Fecha y Hora de Inicio
                                </label>
                                <div className="flex space-x-2">
                                    <input
                                        id="fechaInicio"
                                        type="date"
                                        value={startDate}
                                        onChange={(e) => setStartDate(e.target.value)}
                                        className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    />
                                    <input
                                        id="horaInicio"
                                        type="time"
                                        value={startTime}
                                        onChange={(e) => setStartTime(e.target.value)}
                                        className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    />
                                </div>
                                {errors.startDate && (
                                    <p className="text-red-500 text-sm mt-1">{errors.startDate}</p>
                                )}
                                {errors.startTime && (
                                    <p className="text-red-500 text-sm mt-1">{errors.startTime}</p>
                                )}
                            </div>

                            {/* Selección de fecha de fin */}
                            <div>
                                <label htmlFor="fechaFin" className="block text-gray-700 font-bold mb-2">
                                    Fecha y Hora de Fin
                                </label>
                                <div className="flex space-x-2">
                                    <input
                                        id="fechaFin"
                                        type="date"
                                        value={endDate}
                                        onChange={(e) => setEndDate(e.target.value)}
                                        className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    />
                                    <input
                                        id="horaFin"
                                        type="time"
                                        value={endTime}
                                        onChange={(e) => setEndTime(e.target.value)}
                                        className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    />
                                </div>
                                {errors.endDate && (
                                    <p className="text-red-500 text-sm mt-1">{errors.endDate}</p>
                                )}
                                {errors.endTime && (
                                    <p className="text-red-500 text-sm mt-1">{errors.endTime}</p>
                                )}
                            </div>
                        </div>
                    </div>
                </Popup>
            )}

            {/* Módulo Niko Niko */}
            <Modulo
                mostrarSinEncuestas={false}
                tituloModulo="NIKO NIKO"
                imagenHeader={headerImageNikoNiko}
                imagenBody={bodyImageNikoNiko}
                imagenFooter={footerImageNikoNiko}
                imagenContent={contentImageNikoNiko}
                descripcionModulo="El objetivo de esta práctica es medir el estado de ánimo de los integrantes del equipo y entender cómo se sienten día a día."
                botones={[
                    {
                        texto: "Habilitar Niko Niko",
                        onClick: handleCrearModulo,
                        color: "light_blue",
                    },
                ]}
            />

            {/* Módulo Kudos */}
            <Modulo
                mostrarSinEncuestas={false}
                tituloModulo="KUDOS"
                imagenHeader={headerImageKudos}
                imagenBody={bodyImageKudos}
                imagenFooter={footerImageKudos}
                imagenContent={contentImageKudos}
                descripcionModulo="El objetivo de esta práctica es fomentar el reconocimiento y la gratitud dentro del equipo, generando un ambiente de colaboración."
                botones={[
                    {
                        texto: "Habilitar Kudos",
                        onClick: handleCrearModulo,
                        color: "light_purple",
                    },
                ]}
            />
        </div>
    );
}

export default Modules;
