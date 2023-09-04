export function GetUser(id, token) {
    let url = `http://localhost:8080/api/v1/user/${id}`;
    // Realizar una solicitud POST al backend
    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                throw new Error('Error al recuperar los datos');
            }
        })
}
