export async function UnirseAlEquipo(uuid, name, surname, username, password) {
    const endpoint = `http://localhost:8080/api/v1/regular_user/${uuid}`;
    const datos = {
        name,
        surname,
        username,
        password
      };
    return fetch(endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                console.log(response)
                return Promise.reject('Error de solicitud');
            }
        })
        .catch(error => {
            console.error(error);
            // Devuelve una promesa rechazada en caso de error de red u otro tipo de error
            return Promise.reject(error);
        });
}