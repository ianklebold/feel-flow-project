// Profile.js

// Decodificar el token JWT sin verificar la firma
const decodeJWT = (token) => {
    try {
        const payload = token.split('.')[1]; // El segundo segmento del token es el payload
        const decodedPayload = JSON.parse(atob(payload)); // Decodificar desde Base64
        return decodedPayload;
    } catch (error) {
        console.error("Error al decodificar el token:", error);
        throw new Error("Token inválido.");
    }
};

export const getProfileData = async () => {
    // Recuperar el token de sessionStorage
    const token = sessionStorage.getItem('token'); 
    console.log("Token recuperado:", token);

    // Comprobar si el token es válido
    if (!token) {
        console.error("El token no está disponible en sessionStorage.");
        throw new Error("El token no está disponible en sessionStorage.");
    }

    // Decodificar el token para obtener el ID del usuario
    const { id } = decodeJWT(token);
    console.log("ID recuperado del token:", id);

    try {
        // Realizar la solicitud con el token en el encabezado
        const response = await fetch(`http://localhost:8080/api/v1/user/${id}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`, // El token se pasa aquí en el encabezado
            },
        });

        console.log("Response obtenido:", response);

        // Verificar si la respuesta es exitosa (200)
        if (response.status === 200) {
            const data = await response.json();
            console.log("Datos del perfil:", data);
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
