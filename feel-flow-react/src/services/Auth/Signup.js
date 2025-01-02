
export const Sign_up = async(names, lastName, email, pw, company) => {
    const body = {
        name: names,
        surname: lastName,
        username: email,
        password: pw,
        enterpriseDTO: {
            name: company
        }

    };
    console.log(body);

    try {
        const response = await fetch('http://localhost:8080/api/v1/admin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        })

        if (!response.ok) {
            // Maneja errores HTTP
            const errorData = await response.json();
            throw new Error(errorData.message || "Error en la solicitud");
        }

        const data = await response.json();
        console.log(data)
        // saveAuthData(data.token, data.username);
        return data; // Devuelve la respuesta del backend (por ejemplo, el token)
    } catch (error) {
        // Manejo de errores de red o de la API
        throw new Error(error.message || "Error al comunicarse con el servidor");
    }
}
