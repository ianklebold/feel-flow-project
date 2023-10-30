export async function UpdateUser(id, token, name, surname, username) {
    let url = `http://localhost:8080/api/v1/user/${id}`;
    const changes = {
        name,
        surname,
        username
    }
    try {
        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(changes)
        });

        if (response.status === 204) {
            return true; // Devuelve true si la actualización fue exitosa
        } else if (response.status === 403) {
            window.location.href = '../pages/sign_in.html';
        } else {
            return false; // Devuelve false en caso de otro código de estado
        }
    } catch (error) {
        console.error('Error en la actualización:', error);
        return false; // Devuelve false en caso de error
    }    
}