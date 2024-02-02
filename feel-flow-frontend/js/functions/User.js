export async function Logueado(id, token) {
    let url = `http://localhost:8080/api/v1/user/${id}`;
    const response = await fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    if (response.status === 200) {
        return true;
    } else {
        return false;
    }
}