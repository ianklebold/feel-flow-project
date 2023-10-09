export async function GetUser(id, token) {
    let url = `http://localhost:8080/api/v1/user/${id}`;
    const response = await fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    if (response.status === 200) {
        console.log(response);
        return response.json();
    } else {
        throw new Error('Error al recuperar los datos');
    }
}