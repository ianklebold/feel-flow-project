export function UpdateUser(id, token, name, surname, username) {
    let url = `http://localhost:8080/api/v1/user/${id}`;
    const changes = {
        name,
        surname,
        username
    }
    fetch(url, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(changes)
    })
    .then (response => {
        if (response.status === 204) {
            console.log("exito")
        } else {
            throw new Error('Error al recuperar los datos');
        }
    });
    
}