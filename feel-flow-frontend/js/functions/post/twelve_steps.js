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
            console.log (response.statusMsg);
            console.log (response);
            const data = await response.json();
            console.log (data);
            return data.statusMsg;
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

export async function ObtenerEncuestas(token) {
    const endpoint = `http://localhost:8080/api/v1/surveys`

    try {
        const response = await fetch(endpoint, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const data = await response.json();
            console.log (data);
            return data;
        } else {
            const errorData = await response.json();
            console.error(errorData);
            return errorData;
        }
    } catch (error) {
        console.error('Error al realizar la solicitud:', error);
        return 'Error al realizar la solicitud';
    }
}

export async function ObtenerPreguntas(token, name_module) {
    const endpoint = `http://localhost:8080/api/v1/questions_and_answers/questions/${name_module}`

    try {
        const response = await fetch(endpoint, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const data = await response.json();
            console.log (data);
            return data;
        } else {
            const errorData = await response.json();
            console.error(errorData);
            return errorData;
        }
    } catch (error) {
        console.error('Error al realizar la solicitud:', error);
        return 'Error al realizar la solicitud';
    }
}

export async function ObtenerRespuestas(token, name_module) {
    const endpoint = `http://localhost:8080/api/v1/questions_and_answers/answers/${name_module}`

    try {
        const response = await fetch(endpoint, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const data = await response.json();
            console.log (data);
            return data;
        } else {
            const errorData = await response.json();
            console.error(errorData);
            return errorData;
        }
    } catch (error) {
        console.error('Error al realizar la solicitud:', error);
        return 'Error al realizar la solicitud';
    }
}

export async function EnviarEncuestaTSM(token, datos) {
    const endpoint = `http://localhost:8080/api/v1/surveys/twelve_steps_module`
    const data = datos
    try {
        const response = await fetch(endpoint, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const data = await response.json();
            console.log (data);
            return data;
        } else {
            const errorData = await response.json();
            console.error(errorData);
            return errorData;
        }
    } catch (error) {
        console.error('Error al realizar la solicitud:', error);
        return 'Error al realizar la solicitud';
    }
}