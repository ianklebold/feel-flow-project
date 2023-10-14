export function UpdateEquipo(id, token, nameTeam, descriptionTeam) {
    let url = `http://localhost:8080/api/v1/team/${id}`;
    const changes = {
        nameTeam,
        descriptionTeam
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
            return true;
        } else {
            return false;
        }
    });
    
}