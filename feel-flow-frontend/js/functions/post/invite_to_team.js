export async function InvitarAlEquipo(token, idTeam) {
    const endpoint = `http://localhost:8080/api/v1/team/${idTeam}/invite`;

    return fetch(endpoint, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                if (response.status === 403) {
                    window.location.href = '../pages/sign_in.html';
                }
                // Aquí puedes devolver una promesa rechazada con el mensaje de error deseado
                return Promise.reject('Error de solicitud');
            }
        })
        .catch(error => {
            console.error(error);
            // Devuelve una promesa rechazada en caso de error de red u otro tipo de error
            return Promise.reject(error);
        });
}
