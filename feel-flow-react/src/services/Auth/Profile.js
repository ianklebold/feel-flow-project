// Profile.js
import { getAuthData, getUserData } from "../session";

export const getProfileData = async () => {
    // Recuperar el token de sessionStorage
    const { token } = getAuthData();

    // Comprobar si el token es válido
    if (!token) {
        console.error("No se pudo validar la sesión.");
        throw new Error("El token no está disponible en sessionStorage.");
    }

    const { userID } = getUserData()
    try {
        // Realizar la solicitud con el token en el encabezado
        const response = await fetch(`http://localhost:8080/api/v1/user/${userID}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`, // El token se pasa aquí en el encabezado
            },
        });

        // Verificar si la respuesta es exitosa (200)
        if (response.status === 200) {
            const data = await response.json();
            return data;
        } else {
            console.error(`Error al obtener los datos del perfil: ${response.status} - ${response.statusText}`);
            throw new Error("Error al obtener los datos del perfil.");
        }
    } catch (error) {
        console.error("Error al obtener los datos del perfil:", error);
        throw error;
    }
};
