import React, { useState } from "react";
import { Helmet } from 'react-helmet';
import Button from "../components/Button";
import Popup from "../components/Popup";
import Form from "../components/Form";
import { createTeam } from "../services/Teams/CreateTeam";

function Settings() {
    const [newTeamPopup, setNewTeamIsOpen] = useState(false);
    const [formData, setFormData] = useState({
        nameTeam: "",
        descriptionTeam: "",
        teamLeaderDTO: {
            name: "",
            surname: "",
            username: "",
            password: "",
        }
    });
    const [errors, setErrors] = useState({}); // Error por cada input
    const [success, setSuccess] = useState(); // Éxito de la respuesta de la API
    const [apiError, setError] = useState(); // Error de la respuesta de la API

    const openPopup = (event) => {
        event.preventDefault();
        setNewTeamIsOpen(true);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });

        if (errors[name]) {
            setErrors({ ...errors, [name]: null });
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        console.log("Formulario enviado");
        
        try {
            const result = await createTeam(
                formData.nameTeam,
                formData.descriptionTeam,
                formData.teamLeaderDTO.name,
                formData.teamLeaderDTO.surname,
                formData.teamLeaderDTO.username,
                formData.teamLeaderDTO.password
            );

            if (result) {
                const formattedErrors = result.errors.reduce((acc, curr) => {
                    const key = Object.keys(curr)[0];
                    acc[key] = curr[key];
                    return acc;
                }, {});
                console.log(formattedErrors);
                setErrors(formattedErrors); 
                // console.log(errors.{ teamLeaderDTO.name });
            } else {
                setSuccess("Equipo creado correctamente");
            }
        } catch (error) {
            console.error("Error al crear el equipo:", error);
        }
    };

    return (
        <div>
            <Helmet>
                <title>Settings</title>
            </Helmet>
            {/* Contenido de la página */}

            <div className="p-6">
                <div className="mb-4">
                    <Button
                        label="Crear Equipos"
                        onClick={openPopup}
                        color="blue"
                        size="md"
                        variant="solid"
                    />
                    <Popup
                        isOpen={newTeamPopup}
                        title="Crear nuevo Equipo"
                        // message="Crear un equipo nuevo"
                        buttons={[
                            {
                                label: "Aceptar",
                                onClick: () => { setNewTeamIsOpen(false) },
                                color: "blue",
                            },
                            {
                                label: "Cancelar",
                                onClick: () => { setNewTeamIsOpen(false) },
                                color: "red",
                            },
                        ]}
                    >
                        <div>
                            <Form
                                handleSubmit={handleSubmit}
                                inputs={[
                                    {
                                        label: "Nombre del Equipo",
                                        labelClass: "login",
                                        name: "nameTeam", // Ajusta al nombre correcto en formData
                                        value: formData.nameTeam,
                                        onChange: handleChange, // Pasa solo handleChange
                                        placeholder: "Ingresa el nombre del equipo",
                                        color: "blue",
                                        error: errors.nameTeam
                                    },
                                    {
                                        label: "Descripción",
                                        labelClass: "login",
                                        name: "descriptionTeam",
                                        value: formData.descriptionTeam,
                                        onChange: handleChange,
                                        placeholder: "Ingresa la descripción del equipo",
                                        color: "blue",
                                        error: errors.descriptionTeam,
                                    },
                                    {
                                        label: "Nombre Team Leader",
                                        labelClass: "login",
                                        name: "teamLeaderDTO.name",
                                        value: formData.teamLeaderDTO.name,
                                        onChange: handleChange,
                                        placeholder: "Ingresa el nombre del líder del equipo",
                                        color: "blue",
                                        // error: errors.teamLeaderDTO.name
                                    },
                                    {
                                        label: "Apellido del Team Leader",
                                        labelClass: "login",
                                        name: "teamLeaderDTO.surname",
                                        value: formData.teamLeaderDTO.surname,
                                        onChange: handleChange,
                                        placeholder: "Ingresa el apellido del líder del equipo",
                                        color: "blue",
                                        // error: errors.teamLeaderDTO.surname
                                    },
                                    {
                                        label: "Username del Team Leader",
                                        labelClass: "login",
                                        name: "teamLeaderDTO.username",
                                        value: formData.teamLeaderDTO.username,
                                        onChange: handleChange,
                                        placeholder: "Ingresa el username del líder del equipo",
                                        color: "blue",
                                        // error: errors.teamLeaderDTO.username
                                    },
                                    {
                                        label: "Password del Team Leader",
                                        labelClass: "login",
                                        name: "teamLeaderDTO.password",
                                        value: formData.teamLeaderDTO.password,
                                        type: "password",
                                        onChange: handleChange,
                                        placeholder: "Ingresa la nueva password del líder del equipo",
                                        color: "blue",
                                        // error: errors.teamLeaderDTO.password
                                    },
                                ]}
                                buttons={[
                                    {
                                        label: "Guardar",
                                        type: "submit",
                                        color: "green",
                                        variant: "solid",
                                        className: "w-full",
                                    },
                                ]}
                                error={apiError}
                                success={success}
                            />
                        </div>
                    </Popup>

                </div>
            </div>
        </div>

    );
}
export default Settings;
