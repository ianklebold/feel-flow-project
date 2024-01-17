export async function crearModulo(token, modulo) {
    const endpoint = `http://localhost:8080/api/v1/module/${modulo}`;

    return fetch(endpoint, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (response.status === 200) {
                return true;
            } else {
                if (response.status === 404) {
                    console.error("No se encontró el módulo")
                }
                if (response.status === 400) {
                    console.error("Error, el modulo ya está creado")
                }
                return false;
            }
        })
        .catch(error => {
            console.error(error);
            return false;
        });
}
