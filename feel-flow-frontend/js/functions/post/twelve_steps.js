export async function crearModulo(token, idTeam) {
    const endpoint = `http://localhost:8080/api/v1/twelve_steps_modules/${idTeam}`;

    try {
        const response = await fetch(endpoint, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            return response.statusMsg;
        } else {
            const errorData = await response.json();
            console.error(errorData.errorMessage);
            return errorData.errorMessage;
        }
    } catch (error) {
        console.error('Error al realizar la solicitud:', error);
        return 'Error al realizar la solicitud';
    }
}
